import { TextField, Button} from '@mui/material'
import React, { useState, useContext, useEffect } from 'react';
import UserContext from '../../../context/UserContext';
import SocketContext from '../../../context/SocketContext';
import MessageContainer from './MessageContainer';
import jwtDecode from 'jwt-decode';
import { io } from 'socket.io-client';
import './button.scss';
import Picker from 'emoji-picker-react';


const ChatContainer = ({ currentRoom, getUserDetails }) => {

  const [user, setUser] = useContext(UserContext);
  const [socket, setSocket] = useContext(SocketContext);
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [showPicker, setShowPicker] = useState(false);
 
  const onEmojiClick = (event, emojiObject) => {
    setMessage(prevInput => prevInput + emojiObject.emoji);
    setShowPicker(false);
  };

  useEffect(() => {

    let s = io(`http://crypticsocketio.us-east-1.elasticbeanstalk.com:3003`, { auth: { token: localStorage.getItem('token') } });
    setSocket(s);
    getUserDetails(jwtDecode(localStorage.getItem("token")).sub);

    fetch(`http://cryptic-api.us-east-1.elasticbeanstalk.com/message/room/${currentRoom.roomId}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    }).then((res) => {
      if (res.status != 200) {
        return null;
      }
      return res.json();
    }).then((data) => {
      if (data) {
        setMessages(data);
      } else {
        setMessages([]);
      }
    }).then(() => {
      s.emit('chat message', { messageContent: `User ${jwtDecode(localStorage.getItem("token")).sub} has joined.`, roomId : 1 });
    })
  }, []);

  useEffect(() => {
    // make sure the socket and messages are populated
    if (!socket || messages.length === 0) return;
    socket.on('chat message', (msg) => {

      // make sure we don't keep displaying the same user joining
      console.log(msg); 
      msg.timeStamp = Date.now();
      let newMessages = [...messages];
      if(msg.roomId == currentRoom.roomId) {
        newMessages.push(msg);
      }
      setMessages(newMessages);

    });
  }, [messages, socket])


  const handleMessageChange = (e) => {
    setMessage(e.target.value);
  }

  const submitMessage = (e) => {
    e.preventDefault();

    // check if the message is empty, if it is just return
    if (message.trim() === '' || !message) return;

    console.log(message);
    let newMessages = [...messages];


    // emit broadcast
    socket.emit('chat message', { username: user.username, messageContent: message, timeStamp : Date.now(), roomId: currentRoom.roomId});

    const messageToPost = {
      messageContent: message,
      roomId: currentRoom.roomId,
      userId: user.userId,
      username: user.username,
      timeStamp: Date.now()
    }
    newMessages.push(messageToPost);
    setMessages(newMessages);
    setMessage('');

    // store the message
    fetch(`http://cryptic-api.us-east-1.elasticbeanstalk.com/message`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(messageToPost)
    }).then((res) => {
      if (res.status !== 201) {
        console.log(messageToPost);
        // alert message here that the message didn't send
        return null;
      }
      return;
    });
  }

  const scrollContainer = (ref) => {
    ref.current.scrollIntoView({ behavior: "smooth" });
  }

  if (socket) {
    return (
      <div className='chat-container'>
        <MessageContainer messages={messages} scrollContainer={scrollContainer} />
        <form className='message-form' autoComplete='off' onSubmit={submitMessage}>
          <TextField label='Chatting in Main!' variant='outlined' className='message-input' name='message' value={message} aria-autocomplete='false'
          onChange={handleMessageChange} />
        <img
          className="emoji-icon"
          src="https://icons.getbootstrap.com/assets/icons/emoji-smile.svg"
          onClick={() => setShowPicker(val => !val)} />
        {showPicker && <Picker
          pickerStyle={{ width: '30%' }}
          onEmojiClick={onEmojiClick} />}
          <Button variant='contained' className='chat-input-area__submit-button' type='submit'></Button>
        </form>
      </div>
    )
  } else {
    return <></>;
  }

}

export default ChatContainer