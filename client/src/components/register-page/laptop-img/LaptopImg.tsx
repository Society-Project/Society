import React from 'react'
import laptopPicture from "/src/images/laptopPicture.jpg"
import styled from 'styled-components';

export const LaptopImg = () => {
  return (
    <LaptopPicture className="Image" src={laptopPicture.src} alt="Picture" />
  )
}

const LaptopPicture = styled.img`

  opacity: 0.9;

  :hover {
    cursor: pointer;
    opacity: 1;
  }
}
`;