import { Box } from "@mui/system";
import React, { useState } from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemText from "@mui/material/ListItemText";
import Avatar from "@mui/material/Avatar";
import Divider from "@mui/material/Divider";
import Button from "@mui/material/Button";
import CloseIcon from "@mui/icons-material/Close";
import Modal from "react-modal";
import { Typography } from "@mui/material";
import { MainPageBox } from "../page-box/PageBox";
import "../Styles/MainPage.scss";
import { SearchBar } from "../SearchBar";

const friends = [
  {
    name: "Petya",
    avatar: "../../src/images/laptopPicture.jpg",
    mutualFriends: ["Didi", "Ivo"],
  },
  {
    name: "Didi",
    avatar: "../../src/images/laptopPicture.jpg",
    mutualFriends: ["Petya", "Ivo"],
  },
  {
    name: "Moni",
    avatar: "../../src/images/laptopPicture.jpg",
    mutualFriends: ["Didi", "Ivo"],
  },
  {
    name: "Ivo",
    avatar: "../../src/images/laptopPicture.jpg",
    mutualFriends: [],
  },
  {
    name: "Zahari",
    avatar: "../../src/images/laptopPicture.jpg",
    mutualFriends: [],
  },
  {
    name: "Joro ",
    avatar: "../../src/images/laptopPicture.jpg",
    mutualFriends: [],
  },
];

export const FriendsList = () => {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [modalData, setModalData]: any = useState(null);
  return (
    <Box className="page-box">
      <SearchBar />
      <Box className="Container">
        <Typography className="AllFriendsText"> All friends</Typography>
        <Box id="box">
          <List>
            {friends.map((friend, index) => (
              <React.Fragment key={index}>
                <ListItem>
                  <ListItemAvatar className="Avatar">
                    <Avatar alt={friend.name} src={friend.avatar} />
                  </ListItemAvatar>
                  <ListItemText primary={friend.name} />
                  {friend.mutualFriends.length != 0 && (
                    <Button
                      className="MutualFriendsButton"
                      variant="contained"
                      onClick={() => {
                        setModalData(friend);
                        setModalIsOpen(true);
                      }}
                    >
                      {friend.mutualFriends.length} mutual friends
                    </Button>
                  )}
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
            <Modal
              isOpen={modalIsOpen}
              onRequestClose={() => setModalIsOpen(false)}
              contentLabel="Mutual friends"
              style={modalStyle}
            >
              <Box>
                <button
                  onClick={() => setModalIsOpen(false)}
                  className="close-modal-button"
                >
                  <CloseIcon />
                </button>
                <Typography className="NumberOfMutualFriends">
                  {modalData?.mutualFriends.length} mutual friends
                </Typography>
                <Divider
                  sx={{
                    width: "100%",
                    margin: "10px auto",
                    backgroundColor: "grey",
                    color: "rgba(0, 0, 0, 0.2)",
                    align: "center",
                  }}
                />
                <List>
                  {modalData?.mutualFriends.map(
                    (mutualFriend: string, index: number) => (
                      <ListItem key={index}>
                        <ListItemAvatar className="Avatar">
                          <Avatar alt={mutualFriend} src={""} />
                        </ListItemAvatar>
                        <ListItemText primary={mutualFriend} />
                      </ListItem>
                    )
                  )}
                </List>
              </Box>
            </Modal>
          </List>
        </Box>
      </Box>
    </Box>
  );
};

const modalStyle: any = {
  content: {
    border: "2px solid rgba(74, 122, 99, 1)",
    borderRadius: "18px",
    bottom: "auto",
    height: "200px", // set height
    left: "65.3%",
    padding: "2rem",
    position: "fixed",
    right: "auto",
    top: "45%", // start from center
    transform: "translate(-50%,-100px)", // adjust top "up" based on height
    width: "40%",
    maxWidth: "40rem",
    boxShadow: "0px 0px 10px 0px rgba(74, 122, 99, 1)",
  },
};
