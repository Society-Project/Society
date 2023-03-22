import { useState } from "react";
import Image from "next/image";
import Link from "next/link";

import {
  Box,
  Typography,
  Container,
  Modal,
  Grid,
  List,
  ListItem,
} from "@mui/material";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import WorkIcon from "@mui/icons-material/Work";
import SchoolIcon from "@mui/icons-material/School";
import TodayIcon from "@mui/icons-material/Today";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import PersonAddAltRoundedIcon from "@mui/icons-material/PersonAddAltRounded";

import { Post } from "../../components/posts/Post";
import { CreatePost } from "@/components/posts/createPost/CreatePost";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import { uploadImage } from "@/services/uploadImage";

import profileThumbnail from "../../../public/profileThumbnail.png";
import friendPicture from "../../../public/friendPicture.png";
import defaultUserPicture from "../../../public/defaultUserPicture.png";
import albumPicture from "../../../public/albumPicture.png";

import "./Profile.scss";

const UserProfile = () => {
  const [open, setOpen] = useState(false);
  const [imageSrc, setImageSrc] = useState<
    String | ArrayBuffer | StaticImport | null
  >("");
  const [uploadData, setUploadData] = useState<Object>();

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleOnChange = (changeEvent: React.ChangeEvent<HTMLFormElement>) => {
    const reader = new FileReader();

    reader.onload = function (onLoadEvent) {
      if (onLoadEvent.target) {
        setImageSrc(onLoadEvent.target.result);
      }
      setUploadData(undefined);
    };

    if (changeEvent.target.files) {
      reader.readAsDataURL(changeEvent.target.files[0]);
    }
  };

  const handleOnSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const image = await uploadImage(event);

    handleClose();
    setImageSrc(image.imageUrl);
    setUploadData(image.data);
  };

  return (
    <div className="profile-container">
      <NavigationBar />
      <main className="profile-wrapper">
        <Container>
          <Box className="profile-images">
            <Image
              src={profileThumbnail}
              alt="Profile photo"
              className="profile-banner"
            />
            <Box className="profile-picture-box" onClick={handleOpen}>
              <Box className="profile-image-content">
                {imageSrc !== "" ? (
                  <Image
                    src={imageSrc}
                    alt="Name"
                    className={`profile-picture ${imageSrc !== "" && "change"}`}
                    fill
                  />
                ) : (
                  <Image
                    src={defaultUserPicture}
                    alt="Name"
                    className="profile-picture"
                  />
                )}
                <Box className="profile-add-photo">
                  <AddAPhotoIcon />
                </Box>
              </Box>
            </Box>
          </Box>
          <Box className="profile-user-name">
            <Typography className="profile-name name">Name Name</Typography>
            <button className="profile-add-button add-button">
              <PersonAddAltRoundedIcon />
              <span className="profile-span-friend">Add friend</span>
            </button>
          </Box>

          <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
          >
            <Box className="profile-picture-box">
              <form
                method="post"
                onChange={handleOnChange}
                onSubmit={handleOnSubmit}
              >
                <p>
                  <input type="file" name="file" />
                </p>

                <Image
                  src={imageSrc !== "" ? imageSrc : defaultUserPicture}
                  alt="User photo"
                  width={400}
                  height={400}
                  className="user-photo-container"
                />

                {imageSrc && !uploadData && (
                  <p>
                    <button className="profile-button-upload">
                      Upload Picture
                    </button>
                  </p>
                )}

                {uploadData && (
                  <code>
                    <pre>{JSON.stringify(uploadData, null, 2)}</pre>
                  </code>
                )}
              </form>
            </Box>
          </Modal>
          <Grid container spacing={2} className="profile-content">
            <Grid item xs={4} className="profile-information">
              <List
                sx={{
                  width: "100%",
                  maxWidth: 360,
                  bgcolor: "background.paper",
                }}
                className="profile-information-data information-data"
              >
                <ListItem className="profile-information-item">
                  <LocationOnIcon />
                  Location
                </ListItem>
                <ListItem className="profile-information-item">
                  <WorkIcon />
                  Work
                </ListItem>
                <ListItem className="profile-information-item">
                  <SchoolIcon />
                  Education
                </ListItem>
                <ListItem className="profile-information-item">
                  <TodayIcon />
                  Birthday
                </ListItem>
                <Typography className="profile-edit-link">
                  <Link href="#">Edit</Link>
                </Typography>
              </List>
              <Grid container spacing={2} className="album-grid">
                {[1, 2, 3, 4].map((_, index) => {
                  return (
                    <Grid className="album-grid-content" key={index}>
                      <ListItem className="album-grid-item">
                        <Image
                          src={albumPicture}
                          alt="Profile photo"
                          className="user-picture-item"
                        />
                      </ListItem>
                      {index === 3 && (
                        <Box className="photos-plus">
                          <span className="photos-plus-name">+ photos</span>
                        </Box>
                      )}
                    </Grid>
                  );
                })}
              </Grid>
              <Grid container spacing={2} className="album-grid">
                {[1, 2, 3, 4].map((_, index) => {
                  return (
                    <Grid className="album-grid-content" key={index}>
                      <ListItem className="album-grid-item">
                        <Image
                          src={friendPicture}
                          alt="Profile photo"
                          className="user-picture-item friend"
                          width={110}
                          height={95}
                        />
                      </ListItem>
                      {index === 3 && (
                        <Box className="photos-plus">
                          <span className="photos-plus-name">All friends</span>
                        </Box>
                      )}
                    </Grid>
                  );
                })}
              </Grid>
            </Grid>
            <Grid className="profile-action" item xs={8}>
              <CreatePost className="profile-create-post" />
              <Box className="profile-publiations">
                <Post />
                <Post />
              </Box>
            </Grid>
          </Grid>
        </Container>
      </main>
    </div>
  );
};

export default UserProfile;
