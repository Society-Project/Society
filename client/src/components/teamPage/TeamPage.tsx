import React, { useState } from "react";
import { Box } from "@mui/material";
import "../Styles/MainPage.scss";
import { SearchBar } from "../SearchBar";
import Modal from "react-modal";
import { Typography } from "@mui/material";

import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemText from "@mui/material/ListItemText";
import Avatar from "@mui/material/Avatar";
import Divider from "@mui/material/Divider";
import Button from "@mui/material/Button";
import CloseIcon from "@mui/icons-material/Close";

const team = [
  {
    name: "Petya Petyova",
    avatar: "/src/images/didiPic.jpg",
    personInfo: ["Petya Petyova"],
  },
  {
    name: "Didi Didova",
    avatar: "../../src/images/laptopPicture.jpg",
    personInfo: ["Didi Didova"],
  },
  {
    name: "Moni Monev",
    avatar: "../../src/images/laptopPicture.jpg",
    personInfo: ["Moni Monev"],
  },
  {
    name: "Ivo Ivov",
    avatar: "../../src/images/laptopPicture.jpg",
    personInfo: ["Ivo Ivov"],
  },
  {
    name: "Zahari Zaharev",
    avatar: "../../src/images/laptopPicture.jpg",
    personInfo: ["Zahari Zaharev"],
  },
  {
    name: "Joro Jorev",
    avatar: "../../src/images/laptopPicture.jpg",
    personInfo: ["Joro Jorev"],
  },
];

export const TeamPage = () => {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [modalData, setModalData]: any = useState(null);
  return (
    <Box className="page-box">
      <SearchBar />
      <Box className="Container">
        <Typography className="TeamText">Team</Typography>
        <Box id="box">
          <List>
            {team.map((oneOfUs:any, index:number) => (
              <React.Fragment key={index}>
                <ListItem>
                  <ListItem className="ProfileInformation">
                    <ListItemAvatar className="Avatar">
                      <Avatar alt={oneOfUs.name} src={oneOfUs.avatar} />
                      <ListItemText primary={oneOfUs.name} />
                      <Typography className="PersonRole" variant="body2">
                        Front - End
                      </Typography>
                    </ListItemAvatar>
                    {oneOfUs.personInfo.length != 0 && (
                      <Button
                        className="SeeMoreButton"
                        variant="contained"
                        onClick={() => {
                          setModalData(oneOfUs);
                          setModalIsOpen(true);
                        }}
                      >
                        See more
                      </Button>
                    )}
                  </ListItem>
                </ListItem>
                {index < team.length - 1}
              </React.Fragment>
            ))}
            <Modal
              isOpen={modalIsOpen}
              onRequestClose={() => setModalIsOpen(false)}
              contentLabel="Information"
              style={modalStyle}
            >
              <Box>
                <button
                  onClick={() => setModalIsOpen(false)}
                  className="close-modal-button"
                >
                  <CloseIcon />
                </button>
                <Typography className="Name">
                  {modalData?.personInfo}
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
                <Typography>Information</Typography>
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
    height: "50vh",
    left: "57%",
    padding: "5vh",
    position: "fixed",
    right: "auto",
    top: "48%",
    transform: "translate(-50%,-100px)",
    width: "60%",
    maxWidth: "60rem",
    boxShadow: "0px 0px 10px 0px rgba(74, 122, 99, 1)",
  },
};
