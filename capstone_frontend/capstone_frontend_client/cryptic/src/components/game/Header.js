import React, { useState, useEffect } from 'react'
import { useNavigate, useLocation } from 'react-router-dom';
import MenuIcon from '@mui/icons-material/Menu';
import { AppBar, Toolbar, Typography, IconButton, Menu, MenuItem } from '@mui/material'
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import HomeIcon from '@mui/icons-material/Home';
import LogoutIcon from '@mui/icons-material/Logout';
import InfoIcon from '@mui/icons-material/Info';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import SportsEsportsIcon from '@mui/icons-material/SportsEsports';
import {TextField} from '@mui/material';

const Header = () => {

  const [anchorEl, setAnchorEl] = useState(null);
  const [burgerAnchorEl, setBurgerAnchorEl] = useState(null);
  const [path, setPath] = useState('');
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    setPath(location.pathname);
  }, []);

  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };
   
  const handleBurgerMenu = (e) => {
    setBurgerAnchorEl(e.currentTarget);
  }

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleCloseBurger = () => {
    setBurgerAnchorEl(null);
  }

  const handleLogOut = () => {
    localStorage.removeItem("token");
    navigate('/auth');
  }
  const handleAbout = () => {
    navigate('/about');
    handleClose();
  }
  const handleHome = () => {
    navigate('/home');
    handleClose();
  }

  const handleHomeTwo = () => {
    navigate('/hometwo');
    handleClose();
  }

  const handleHomeThree = () => {
    navigate('/homethree');
    handleClose();
  }

  const handleCalendar = () => {
    navigate('/calendar');
    handleClose();
  }

  const handleGame = () => {
    navigate('/game');
    handleClose();
  }

  const handleChangeRoom = (e) => {
    // where we would swap rooms
    handleCloseBurger();
  }


  return (
    <AppBar position="static">
      <Toolbar>
      
      <img src="images/cryptic_mini.png" alt="logo" />
      
      <div className="single-room-name">
      <h1>
        Game
        </h1>
        </div>
        {
          
          path === '/home' ?
            <>
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu-appbar-burger"
                aria-controls="burger-menu"
                sx={{ mr: 2 }}
                className='burger-menu-header'
                onClick={handleBurgerMenu}
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="burger-menu"
                anchorEl={burgerAnchorEl}
                keepMounted
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'left',
                }}
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'left',
                }}
                open={Boolean(burgerAnchorEl)}
                onClose={handleCloseBurger}
              >
                <MenuItem onClick={handleChangeRoom}></MenuItem>
              </Menu>
            </> :
            <></>
        }
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          <span
            className='home-link'
            onClick={() => navigate("/home")}></span>
        </Typography>
        {
          path === '/hometwo' ?
            <>
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu-appbar-burger"
                aria-controls="burger-menu"
                sx={{ mr: 2 }}
                className='burger-menu-header'
                onClick={handleBurgerMenu}
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="burger-menu"
                anchorEl={burgerAnchorEl}
                keepMounted
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'left',
                }}
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'left',
                }}
                open={Boolean(burgerAnchorEl)}
                onClose={handleCloseBurger}
              >
                <MenuItem onClick={handleChangeRoom}>Two</MenuItem>
              </Menu>
            </> :
            <></>
        }
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          <span
            className='home-link'
            onClick={() => navigate("/hometwo")}></span>
        </Typography>
          <div>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleMenu}
              color="inherit"
            >
              <AccountBoxIcon  fontSize="large" />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorEl}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorEl)}
              onClose={handleClose}
            >
              <MenuItem onClick={handleLogOut}><LogoutIcon fontSize="medium" /></MenuItem>
              <MenuItem onClick={handleAbout}><InfoIcon fontSize="medium" /></MenuItem>
              <MenuItem onClick={handleHome}><HomeIcon fontSize="medium" /></MenuItem>
              <MenuItem onClick={handleHomeTwo}><HomeIcon fontSize="medium" color="inherit" label="Room Two"/></MenuItem>
              <MenuItem onClick={handleHomeThree}><HomeIcon fontSize="medium" color="inherit" label="Room Three"/></MenuItem>
              <MenuItem onClick={handleCalendar}><CalendarMonthIcon fontSize="medium" /></MenuItem>
              <MenuItem onClick={handleGame}><SportsEsportsIcon fontSize="medium" /></MenuItem>
            </Menu>
          </div>
      </Toolbar>
    </AppBar>
  )
}

export default Header