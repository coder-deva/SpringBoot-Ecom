import AccountCircle from "@mui/icons-material/AccountCircle";
import AddBoxIcon from "@mui/icons-material/AddBox";
import DashboardIcon from "@mui/icons-material/Dashboard";

import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import {
  AppBar,
  Box,
  CssBaseline,
  Divider,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Menu,
  MenuItem,
  Toolbar,
  Typography,
} from "@mui/material";
import * as React from "react";
import { useState } from "react";
import { FaClipboardList } from "react-icons/fa";
import { useDispatch, useSelector } from "react-redux";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { setUserDetails } from "../../store/actions/UserAction";

const drawerWidth = 240;

const sidebarItems = [
  { text: "Dashboard", icon: <DashboardIcon />, path: "/admin" },
  { text: "Add Seller", icon: <AddBoxIcon />, path: "/admin/addseller" },
//   {
//     text: "List Product",
//     icon: <FaClipboardList />,
//     path: "/seller/listproducts",
//   },
//   { text: "Orders", icon: <ShoppingCartIcon />, path: "/seller/orders" },
  // { text: 'Inbox', icon: <InboxIcon />, path: '/seller/inbox' },
  // { text: 'Mail', icon: <MailIcon />, path: '/seller/mail' },
];

export default function AdminDashboard() {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();

  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>LogOut>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  const [user] = useState(useSelector((state) => state.user));

  const handleLogout = () => {
    handleMenuClose();
    let u = {
      username: "",
      role: "",
    };
    setUserDetails(dispatch)(u);
    localStorage.clear();
    navigate("/");
  };

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />

      {/* Top AppBar */}
      <AppBar
        position="fixed"
        sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}
      >
        <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="h6" noWrap component="div">
            Admin Panel
          </Typography>

          {/* Profile Avatar and Welcome */}
          <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
            <Typography variant="body1" component="div">
              Welcome {user.username} - {user.role}
            </Typography>
            <IconButton onClick={handleMenuOpen} size="large" color="inherit">
              <AccountCircle />
            </IconButton>
            <Menu
              anchorEl={anchorEl}
              open={open}
              onClose={handleMenuClose}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "right",
              }}
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
            >
              <MenuItem
                onClick={() => {
                  handleMenuClose();
                  navigate("/admin/profile");
                }}
              >
                Profile
              </MenuItem>
              <MenuItem onClick={() => handleLogout()}>Logout</MenuItem>
            </Menu>
          </Box>
        </Toolbar>
      </AppBar>

      {/* Sidebar Drawer */}
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: {
            width: drawerWidth,
            boxSizing: "border-box",
          },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: "auto" }}>
          <List>
            {sidebarItems.map((item) => (
              <ListItem
                key={item.text}
                disablePadding
                selected={location.pathname === item.path}
              >
                <ListItemButton onClick={() => navigate(item.path)}>
                  <ListItemIcon>{item.icon}</ListItemIcon>
                  <ListItemText primary={item.text} />
                </ListItemButton>
              </ListItem>
            ))}
          </List>
          <Divider />
        </Box>
      </Drawer>

      {/* Main Content */}
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        <Outlet />
      </Box>
    </Box>
  );
}
