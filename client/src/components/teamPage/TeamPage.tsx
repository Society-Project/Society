import React, { useState } from "react";
import { Box } from "@mui/material";
import "../Styles/MainPage.scss";
import { SearchBar } from "../SearchBar";
import Modal from "react-modal";
import { Typography } from "@mui/material";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import GitHubIcon from "@mui/icons-material/GitHub";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
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
    personInfo: "Petya Marinova",
    role: "Front - End",
    linkedin: "https://www.linkedin.com/in/pmmarinova/",
    github: "https://github.com/petya0111",
    info: "Software developer with a background in Frontend and Backend technologies. Frontend experience with Angular & Typescript technologies and working experience with backend projects with Java, Kotlin, Spring Boot, also mobile projects with Android. I have developed personal projects with React, NextJS, Solidity, NodeJS, Android & Python. Currently, I am exploring the Blockchain Web3 space with Hardhat and Solidity. Curious about the security-vulnerable Solidity code. I am interested in IT conferences and networking. Glad to take part in different aspects of courses and workshops.",
  },
  {
    name: "Dimitrina Yordanova",
    avatar: DimitrinaPhoto.src,
    personInfo: "Dimitrina Yordanova",
    role: "Front - End",
    linkedin: "https://www.linkedin.com/in/dimitrina-yordanova-81a659245/",
    github: "https://github.com/di-yordanova",
    info: "Frontend Developer with React, NextJS, TypeScript technologies. Participation in team project Society Social Network as Frontend Developer and UX Designer. UX Design skills with Figma.",
  },
  {
    name: "Simeon Cholakov",
    avatar: SimeonPhoto.src,
    personInfo: "Simeon Cholakov",
    role: "Front - End",
    linkedin: "https://www.linkedin.com/in/simeon-cholakov/",
    github: "https://github.com/cholakovsimeon",
    info: "Motivated and hardworking person with interest in web3 security and building web applications. I have around a year background in web2 using JavaScript, more specifically with the MERN stack. But currently, I am focused on web3 security as a smart contract auditor.",
  },
  {
    name: "Ivaylo Slavchev",
    avatar: IvayloPhoto.src,
    personInfo: "Ivaylo Slavchev",
    role: "Front - End",
    linkedin: "https://www.linkedin.com/in/ivaylo-slavchev-6425a521b/",
    github: "https://github.com/IvayloSlavchev",
    info: "Software engineer who is interested in building web applications. I love to do the Front-End work, but I also have some Back-End experience (with Node.js + express), so if you searching a Front-End developer I’d love to make connection with you. I use React as my main library when building application, but I also work on a project where we use Next.js with MUI.",
  },
  {
    name: "Zahari Cheyrekov",
    avatar: ZahariPhoto.src,
    personInfo: "Zahari Cheyrekov",
    role: "Front - End",
    linkedin: "https://www.linkedin.com/in/zahari-cheyrekov-5647a9227/",
    github: "https://github.com/ZahariCheyrekov",
    info: "Passionate and hardworking Web Developer who loves to write code and solve problems. I have interest in Web Development and Software Engineering fields and am seeking new and exciting opportunities related to them. JavaScript is my main programming language and have previous experience creating web applications with MERN stack, but I consider myself a fast learner and I don't have any problems working with new technologies and getting the work done. Personal development is a hobby of mine. I love to read books about it and share different thoughts and ideas with people. My time outside of programming is usually spent reading books, socializing, listening to music, playing chess and weightlifting.",
  },
  {
    name: "Georgi Peev",
    avatar: GeorgiPhoto.src,
    personInfo: "Georgi Peev",
    role: "Back - End",
    linkedin: "https://www.linkedin.com/in/georgipeev/",
    github: "https://github.com/PeevG",
    info: "Java web developer with a focus on back-end development. My primary interests lie in building robust and efficient back-end systems using Java and related technologies. I am always motivated to grow and learn new technologies to create high-quality and functional software.",
  },
  {
    name: "Pavel Pindarev",
    avatar: PavelPhoto.src,
    personInfo: "Pavel Pindarev",
    role: "Back - End",
    linkedin: "https://www.linkedin.com/in/pavel-pindarev/",
    github: "https://github.com/PavelPindarev",
    info: "Student, who is interested in Software development, especially in Java Web Development. Currently working on side-project called Society Social Network as Java Back-End Developer. Looking for job to gain some experience in this area and see more work on real projects.",
  },
  {
    name: "Bozhidar Valkov",
    avatar: BozhidarPhoto.src,
    personInfo: "Bozhidar Valkov",
    role: "Back - End",
    linkedin: "https://www.linkedin.com/in/bozhidar-valkov-9b10ab24b/",
    github: "https://github.com/beval1",
    info: "2nd year student at Technical University of Sofia who is always looking for a challenge. I have had passion for computers and technology as long as I can remember. Currently interested in web development. I have worked on several personal project with Java, Spring Boot, Angular, React, Docker.",
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
                    <div className="AvatarContainer">
                      <div className="AvatarAndName">
                        <img
                          className="Avatar"
                          src={oneOfTheTeam.avatar}
                          alt={oneOfTheTeam.name}
                        />
                        <ListItemText
                          className="FirstAndLastName"
                          primary={oneOfTheTeam.name}
                        />
                      </div>
                      <Box className="PersonRoleAndGithubAndLinkeInIcons">
                        <ListItemText
                          className="PersonRole"
                          primary={oneOfTheTeam.role}
                        />
                        <Button
                          component="a"
                          href={oneOfTheTeam.linkedin}
                          target="_blank"
                          rel="noopener noreferrer"
                        >
                          <LinkedInIcon
                            style={{ fontSize: "40px", color: "#0871bd" }}
                          />
                        </Button>
                        <Button
                          component="a"
                          href={oneOfTheTeam.github}
                          target="_blank"
                          rel="noopener noreferrer"
                        >
                          <GitHubIcon
                            style={{ fontSize: "40px", color: "black" }}
                          />
                        </Button>
                      </Box>

                      <div className="SeeMoreButtonContainer">
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
                      </div>
                    </div>
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
                <Typography
                  className="Name"
                  color="rgba(74, 122, 99, 1)"
                  fontSize={"18px"}
                >
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
                <Typography marginLeft={2}>{modalData?.info}</Typography>
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
