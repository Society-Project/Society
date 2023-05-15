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
import Divider from "@mui/material/Divider";
import Button from "@mui/material/Button";
import CloseIcon from "@mui/icons-material/Close";
import PetyaPhoto from "src/images/Petya.jpg";
import DimitrinaPhoto from "src/images/Dimitrina.jpg";
import SimeonPhoto from "src/images/Simeon.jpg";
import IvayloPhoto from "src/images/Ivaylo.jpg";
import ZahariPhoto from "src/images/Zahari.jpg";
import GeorgiPhoto from "src/images/Georgi.jpg";
import PavelPhoto from "src/images/Pavel.jpg";
import BozhidarPhoto from "src/images/Bozhidar.jpg";

const team = [
  {
    name: "Petya Marinova",
    avatar: PetyaPhoto.src,
    personInfo: ["Petya Marinova"],
    role: "Front - End",
  },
  {
    name: "Dimitrina Yordanova",
    avatar: DimitrinaPhoto.src,
    personInfo: ["Dimitrina Yordanova"],
    role: "Front - End",
  },
  {
    name: "Simeon Cholakov",
    avatar: SimeonPhoto.src,
    personInfo: ["Simeon Cholakov"],
    role: "Front - End",
  },
  {
    name: "Ivaylo Slavchev",
    avatar: IvayloPhoto.src,
    personInfo: ["Ivaylo Slavchev"],
    role: "Front - End",
  },
  {
    name: "Zahari Cheyrekov",
    avatar: ZahariPhoto.src,
    personInfo: ["Zahari Cheyrekov"],
    role: "Front - End",
  },
  {
    name: "Georgi Peev",
    avatar: GeorgiPhoto.src,
    personInfo: ["Georgi Peev"],
    role: "Back - End",
  },
  {
    name: "Pavel Pindarev",
    avatar: PavelPhoto.src,
    personInfo: ["Pavel Pindarev"],
    role: "Back - End",
  },
  {
    name: "Bozhidar Valkov",
    avatar: BozhidarPhoto.src,
    personInfo: ["Bozhidar Valkov"],
    role: "Back - End",
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
            {team.map((oneOfTheTeam: any, index: number) => (
              <React.Fragment key={index}>
                <ListItem>
                  <Box className="ProfileInformation">
                    <ListItemText
                      className="PersonRole"
                      primary={oneOfTheTeam.role}
                    />
                    <Box className="AvatarContainer">
                      <img
                        className="Avatar"
                        src={oneOfTheTeam.avatar}
                        alt={oneOfTheTeam.name}
                      />
                      {oneOfTheTeam.personInfo.length !== 0 && (
                        <Button
                          className="SeeMoreButton"
                          variant="contained"
                          onClick={() => {
                            setModalData(oneOfTheTeam);
                            setModalIsOpen(true);
                          }}
                        >
                          See more
                        </Button>
                      )}
                    </Box>
                    <Box className="ListItemTextContainer">
                      <ListItemText
                        className="FirstAndLastName"
                        primary={oneOfTheTeam.name}
                      />
                    </Box>
                  </Box>
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
