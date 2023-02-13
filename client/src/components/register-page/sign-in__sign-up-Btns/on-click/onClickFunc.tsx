import React, { useState } from 'react'

export const onClickFunc = () => {
    const [clicked, setClicked] = useState(false)
  return (
    <div>onClickFunc</div>
  )
}

export const onClickObj = {
    height: '7vh',
    width: '8vw',
    fontSize: '1.2rem',
    borderRadius: '0.5rem'
}
