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
import "../Styles/MainPage.scss";
import { SearchBar } from "../SearchBar";
import PetyaPhoto from "src/images/Petya.jpg";
import DimitrinaPhoto from "src/images/Dimitrina.jpg";
import SimeonPhoto from "src/images/Simeon.jpg";
import IvayloPhoto from "src/images/Ivaylo.jpg";
import ZahariPhoto from "src/images/Zahari.jpg";
import GeorgiPhoto from "src/images/Georgi.jpg";
import PavelPhoto from "src/images/Pavel.jpg";
import BozhidarPhoto from "src/images/Bozhidar.jpg";


const friends = [
  {
    name: "Petya Marinova",
    avatar: PetyaPhoto.src,
    mutualFriends: [
      {
        name: "Dimitrina Yordanova",
        avatar: DimitrinaPhoto.src,
      },
      {
        name: "Ivaylo Slavchev",
        avatar: IvayloPhoto.src,
      },
    ],
  },
  {
    name: "Dimitrina Yordanova",
    avatar: DimitrinaPhoto.src,
    mutualFriends: [
      {
        name: "Petya Marinova",
        avatar: PetyaPhoto.src,
      },
      {
        name: "Ivaylo Slavchev",
        avatar: IvayloPhoto.src,
      },
    ],
  },
  {
    name: "Simeon Cholakov",
    avatar: SimeonPhoto.src,
    mutualFriends: [
      {
        name: "Dimitrina Yordanova",
        avatar: DimitrinaPhoto.src,
      },
      {
        name: "Ivaylo Slavchev",
        avatar: IvayloPhoto.src,
      },
      {
        name: "Zahari Cheyrekov",
        avatar: ZahariPhoto.src,
      },
    ],
  },
  {
    name: "Ivaylo Slavchev",
    avatar: IvayloPhoto.src,
    mutualFriends: [],
  },
  {
    name: "Zahari Cheyrekov",
    avatar: ZahariPhoto.src,
    mutualFriends: [],
  },
  {
    name: "Georgi Peev",
    avatar: GeorgiPhoto.src,
    mutualFriends: [],
  },
  {
    name: "Pavel Pindarev",
    avatar: PavelPhoto.src,
    mutualFriends: [],
  },
  {
    name: "Bozhidar Valkov",
    avatar: BozhidarPhoto.src,
    mutualFriends: [],
  },
];

friends.sort((a, b) => a.name.localeCompare(b.name));

export const FriendsList = () => {
  const [modalIsOpen, setModalIsOpen] = useState<boolean>(false);
  const [modalData, setModalData] = useState({
    name: "",
    avatar: "",
    mutualFriends: [],
  });
  return (
    <Box className="page-box">
      <SearchBar />
      <Box className="Container">
        <Typography className="AllFriendsText"> All friends</Typography>
        <Box id="box">
          <List>
            {friends.map((friend: any, index: number) => (
              <React.Fragment key={index}>
                <ListItem>
                  <ListItemAvatar className="Avatar">
                    <img alt={friend.name} src={friend.avatar} />
                  </ListItemAvatar>
                  <ListItemText
                    className="FirstAndLastName"
                    primary={friend.name}
                  />
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
                    (mutualFriend: any, index: number) => (
                      <React.Fragment key={index}>
                        <ListItem key={index}>
                          <ListItemAvatar className="MutualFriendPhoto">
                            <Avatar
                              alt={mutualFriend.name}
                              src={mutualFriend.avatar}
                            />
                          </ListItemAvatar>
                          <ListItemText primary={mutualFriend.name} />
                        </ListItem>
                      </React.Fragment>
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
    height: "45vh",
    left: "55%",
    padding: "5vh",
    position: "fixed",
    right: "42%",
    top: "48%",
    transform: "translate(-50%,-100px)",
    width: "55%",
    maxWidth: "45%",
    boxShadow: "0px 0px 10px 0px rgba(74, 122, 99, 1)",
  },
};
