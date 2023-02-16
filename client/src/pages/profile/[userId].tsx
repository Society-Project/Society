import {
  Box,
  Button,
  Typography,
  Container,
  Modal,
  Grid,
  List,
  ListItem,
} from "@mui/material";
import { useState } from "react";
import Image from "next/image";
import { CreatePost } from "@/components/posts/createPost/CreatePost";

import profileThumbnail from "../../../public/profileThumbnail.png";
import defaultUserPicture from "../../../public/defaultUserPicture.png";
import albumPicture from "../../../public/albumPicture.png";
import AddAPhotoIcon from "@mui/icons-material/AddAPhoto";
import PersonAddAltRoundedIcon from "@mui/icons-material/PersonAddAltRounded";

import LocationOnIcon from "@mui/icons-material/LocationOn";
import WorkIcon from "@mui/icons-material/Work";
import SchoolIcon from "@mui/icons-material/School";
import TodayIcon from "@mui/icons-material/Today";
import { Post } from "../../components/posts/Post";

import "./Profile.scss";

const UserProfile = () => {
  const [open, setOpen] = useState<Boolean>(false);
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

  const handleOnChange = (changeEvent: React.ChangeEvent<HTMLInputElement>) => {
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

  const handleOnSubmit = async (event: any) => {
    event.preventDefault();

    const form = event.currentTarget;
    const fileInput: File[] | undefined = Array.from(form.elements).find(
      ({ name }) => name === "file"
    );

    const formData = new FormData();

    for (const file of fileInput.files) {
      formData.append("file", file);
    }

    formData.append(
      "upload_preset",
      `${process.env.NEXT_PUBLIC_CLOUDINARY_UPLOAD_PRESET}`
    );

    const data = await fetch(`${process.env.NEXT_PUBLIC_CLOUDINARY_LINK}`, {
      method: "POST",
      body: formData,
    }).then((res) => res.json());

    handleClose();
    setImageSrc(data.secure_url);
    setUploadData(data);
  };

  return (
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
            Add friend
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
              sx={{ width: "100%", maxWidth: 360, bgcolor: "background.paper" }}
              className="profile-information-data information-data"
            >
              <ListItem>
                <LocationOnIcon />
                Location
              </ListItem>
              <ListItem>
                <WorkIcon />
                Work
              </ListItem>
              <ListItem>
                <SchoolIcon />
                Education
              </ListItem>
              <ListItem>
                <TodayIcon />
                Birthday
              </ListItem>
              <Typography className="profile-edit-link">
                <a href="#">Edit</a>
              </Typography>
            </List>

            <Grid container spacing={2} className="album-grid">
              <Grid>
                <ListItem className="album-grid-item">
                  <Image
                    src={albumPicture}
                    alt="Profile photo"
                    className="user-picture-item"
                  />
                </ListItem>
              </Grid>
              <Grid>
                <ListItem className="album-grid-item">
                  <Image
                    src={albumPicture}
                    alt="Profile photo"
                    className="user-picture-item"
                  />
                </ListItem>
              </Grid>
              <Grid>
                <ListItem className="album-grid-item">
                  <Image
                    src={albumPicture}
                    alt="Profile photo"
                    className="user-picture-item"
                  />
                </ListItem>
              </Grid>
              <Grid>
                <ListItem className="album-grid-item">
                  <Image
                    src={albumPicture}
                    alt="Profile photo"
                    className="user-picture-item"
                  />
                </ListItem>
              </Grid>
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
  );
};

export default UserProfile;
