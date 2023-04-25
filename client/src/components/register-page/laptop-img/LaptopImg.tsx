import laptopPicture from '../../../images/laptopPicture.jpg'
import styled from 'styled-components';

export const LaptopImg = () => {
  return (
    <LaptopPicture className="Image" src={laptopPicture.src} alt="Picture" />
  )
}

const LaptopPicture = styled.img`

  opacity: 0.9;

}
`;