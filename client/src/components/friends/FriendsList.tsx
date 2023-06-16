import React, { useState } from "react";
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Avatar,
  Divider,
  Button,
  TextField,
} from "@mui/material";
import { styled } from "@mui/system";
import Modal from "react-modal";
import SearchIcon from "@mui/icons-material/Search";
import CloseIcon from "@mui/icons-material/Close";
import ClearIcon from "@mui/icons-material/Clear";
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

const SearchWrapper = styled(Box)`
  display: flex;
  align-items: center;
  padding: 10px;
  margin-bottom: 10px;
`;

const SearchInput = styled(TextField)`
  flex-grow: 1;
  margin-right: 10px;
`;

export const FriendsList = () => {
  const [modalIsOpen, setModalIsOpen] = useState<boolean>(false);
  const [modalData, setModalData] = useState({
    name: "",
    avatar: "",
    mutualFriends: [] as { name: string; avatar: string }[],
  });
  const [searchQuery, setSearchQuery] = useState("");

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(event.target.value);
  };

  const filteredFriends = friends.filter((friend) =>
    friend.name.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <Box className="page-box">
      <SearchBar />
      <Box className="Container">
        <Typography className="AllFriendsText"> All friends</Typography>
        <Box id="box">
          <List>
            <Box className="SearchBarInFriendsList">
              <SearchWrapper>
                <SearchInput
                  value={searchQuery}
                  onChange={handleSearchChange}
                  placeholder="Search friends"
                  variant="outlined"
                  size="small"
                  InputProps={{
                    startAdornment: <SearchIcon />,
                    color: "success",
                  }}
                />
                {searchQuery && (
                  <Button
                    variant="outlined"
                    size="small"
                    startIcon={<ClearIcon />}
                    onClick={() => setSearchQuery("")}
                    sx={{
                      borderRadius: "50px",
                      width: "2%",
                      marginLeft: "auto",
                    }}
                  ></Button>
                )}
              </SearchWrapper>
              <Divider
                sx={{
                  width: "95%",
                  margin: "10px auto",
                  color: "rgba(0, 0, 0, 0.6)",
                  align: "center",
                }}
              />
            </Box>

            {filteredFriends.map((friend, index) => (
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
