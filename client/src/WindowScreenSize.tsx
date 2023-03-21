import { useState, useEffect } from 'react';

const WindowScreenSize = () => {
    const [width, setWidth]: any = useState(0);
    const [height, setHeight]: any = useState(0);

    const handleWindowResize = () => {
        setWidth(window.innerWidth);
        setHeight(window.innerHeight);
    }

    useEffect(() => {
        handleWindowResize();
        window.addEventListener('resize', handleWindowResize);
        return () => window.removeEventListener('resize', handleWindowResize);
    }, [])

    return [width, height];
}

export default WindowScreenSize