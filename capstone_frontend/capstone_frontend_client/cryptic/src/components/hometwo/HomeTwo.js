import React, { useEffect, useContext, useState, Suspense } from 'react'
import { io } from 'socket.io-client';
import SocketContext from '../../context/SocketContext';
import UserContext from '../../context/UserContext';
import Header from './Header';
import RoomContainer from './rooms/RoomContainer';
import ChatContainer from './chat/ChatContainer';
import { useNavigate } from 'react-router-dom';
import jwtDecode from 'jwt-decode';

const HomeTwo = () => {

  const [socket, setSocket] = useContext(SocketContext);
  const [user, setUser] = useContext(UserContext);
  const [currentRoom, setCurrentRoom] = useState({
    roomId: 2,
    name: "test room 2"
  });
  const navigate = useNavigate();

  useEffect(() => {

    if(!localStorage.getItem("token")) {
      navigate('/auth');
      return;
    }

    setUser({username: jwtDecode(localStorage.getItem("token")).sub});

  }, []);

  const getUserDetails = (username) => {
    fetch(`http://cryptic-api.us-east-1.elasticbeanstalk.com/user/${username}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    }).then((res) => {
      if(res.status != 200) {
        return;
      }

      return res.json();
    }).then((data) => {
      if(data) {
        setUser({
          username: data.username,
          userId: data.appUserId
        });
      }
    });
  }

  return (
    <div className='landing-container'>
      <Header />
      <div className='room-chat-container'> 
        <RoomContainer setCurrentRoom={setCurrentRoom} />
        <ChatContainer currentRoom={currentRoom} getUserDetails={getUserDetails} />
      </div>
    </div>
  )
}

export default HomeTwo