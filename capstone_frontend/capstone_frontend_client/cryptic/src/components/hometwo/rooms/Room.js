import React, { useState, useEffect } from 'react'
import RoomContainer from './RoomContainer';

const Room = ({ room, setCurrentRoom }) => {

  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    if(room.name === 'test room 2') {
      setIsActive(true);
    }
  }, []);

  const handleRoomChange = (e) => {
    // function that will eventually swap a room to active
    setIsActive(true);
    setCurrentRoom(room);
  }

  return (
    <div className={isActive ? 'single-room-container active-room' : 'single-room-container'} name={room.name} onClick={handleRoomChange}>
      <h1 className='single-room-name'>{}</h1>
    </div>
  )
}

export default Room