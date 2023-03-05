import { Box } from "@mui/system";
import React from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemText from "@mui/material/ListItemText";
import Avatar from "@mui/material/Avatar";
import Divider from "@mui/material/Divider";
import Button from "@mui/material/Button";
import { Typography } from "@mui/material";

const friends = [
  { name: "Petya", avatar: "../../src/images/laptopPicture.jpg" },
  { name: "Didi", avatar: "/path/to/bob-avatar.png" },
  { name: "Moni", avatar: "/path/to/charlie-avatar.png" },
  { name: "Ivo", avatar: "/path/to/charlie-avatar.png" },
  { name: "Zahari", avatar: "/path/to/charlie-avatar.png" },
  { name: "Joro ", avatar: "/path/to/charlie-avatar.png" },
];

export const FriendsList = () => {
  return (
    <Box className="Container">
      <Typography className="AllFriendsText"> All friends</Typography>
      <Box id="box">
        <List>
          {friends.map((friend, index) => (
            <React.Fragment key={friend.id}>
              <ListItem>
                <ListItemAvatar className="Avatar">
                  <Avatar alt={friend.name} src={friend.avatar} />
                </ListItemAvatar>
                <ListItemText primary={friend.name} />
                <Button
                  className="MutualFriendsButton"
                  variant="contained"
                >
                  2 mutual friends
                </Button>
              </ListItem>
              {index < friends.length - 1 && (
                <Divider
                  sx={{
                    width: "95%",
                    margin: "18px auto",
                    backgroundColor: "grey",
                    color: "rgba(0, 0, 0, 0.4)",
                    align: "center",
                    opacity: "0.4",
                  }}
                />
              )}
            </React.Fragment>
          ))}
        </List>
      </Box>
    </Box>
  );
};
