import { useState } from 'react';
import { Box, Button } from '@mui/material';
import "../Styles/SettingsPageBody.scss";
import useWindowScreenSize from '@/useWindowScreenSize';

import headerLogo from '../../images/logo-mark.svg';
import expandIcon from '../../images/Vector.png';

import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';

interface TemplateUser {
    labels: string[],
    userData: string[]
}

interface AccountInformation {
    labels: string[],
    userAccountInformation: string[]
}

const SettingsPageBody = () => {
    const [width, height] = useWindowScreenSize();
    const [isSearchIconOnMobileClicked, setIsSearchIconOnMobileClicked] = useState<boolean>(false);
    const [userSearchInput, setUserInputSearch] = useState<string>("");

    const userTemplateObject: TemplateUser[] = [
        { 
        labels: ["First Name", "Last Name", "Location", "Job", "Education"],
        userData: ["Peter", "Anderson", "New York", "Software engineer", "Computer science"]
        }
    ]
    const userAccountInformation: AccountInformation[] = [
        {
            labels: ["Email address", "Add new email", "Username", "Password"],
            userAccountInformation: ["peter@example.com", "", "Peter", ""]
        }
    ]


    const searchIconHandler = () => {
        setIsSearchIconOnMobileClicked(oldState => !oldState);
    }
    const clearButtonHandler = () => {
        setUserInputSearch("");
    }



    return (
        <Box className={width < 1200 ? 'setting-page-mobile' : 'settings-page-body'}>

            <Box className='header-section'>
                {
                    width < 1200 ? <Box className='search-bar-on-tablet-and-mobile'>
                        <img src={headerLogo.src} className='header-logo-on-tablet-and-mobile' alt="Society logo" />

                        <Box className='search-components-on-mobile'>
                            <SearchIcon className="search-icon" onClick={searchIconHandler} />

                            {
                                isSearchIconOnMobileClicked ? <Box className='user-input-and-close-button'>
                                    <input
                                        type="text"
                                        placeholder="Search..."
                                        className="user-input"
                                        value={userSearchInput}
                                        onChange={(event) => setUserInputSearch(event.target.value)}
                                    />
                                    <CloseIcon className="close-button" onClick={clearButtonHandler} />
                                </Box> : null
                            }
                            <img src={expandIcon.src} alt="Show more icon" className='expand-icon' />
                        </Box>
                    </Box> : null
                }
            </Box>
            <p className='page-title'>Settings</p>

            <Box className='settings-box'>
                <h2 className="profile-information-heading">Profile information</h2>

                <Box className='profile-information-box'> 
                    {
                        userTemplateObject.map((item: any, index: number) => {
                            return <div key={index} className='user-data'>
                                <div className='label-class'>
                                    {
                                        item.labels.map((item: any, index: number) => {
                                            return <label key={index} style={{ display: 'block', marginBottom: '1.2rem', fontSize: width < 1200 ?  '18px' : '24px' }}>{item}</label>
                                        })
                                    }
                                </div>
                                <div className='input-fields'>
                                    {
                                        item.userData.map((item: any, index: number) => {
                                            return <input type="text" key={index} value={item} className='profile-input-fields' />
                                        })
                                    }
                                </div>
                            </div>
                        })
                    }
                </Box>

                <h2 className='account-information'>Account information</h2>

                <Box className='account-information-box'>
                    {
                        userAccountInformation.map((item: any, index: number) => {
                            return <div key={index} className='user-account-data'>
                                <div className='labels-account-information'>
                                    {
                                        item.labels.map((labelText: string, index: number) => {
                                            return <label key={index} style={{ display: 'block', marginBottom: '1.2rem', fontSize: width < 1200 ?  '18px' : '24px' }}>{labelText}</label>
                                        })
                                    }
                                </div>

                                <div className='account-input-fields'>
                                    {
                                        item.userAccountInformation.map((item: string, index: number) => {
                                            return <input type="text" key={index} value={item} className='account-input-fields' readOnly />
                                        })
                                    }
                                </div>
                            </div>
                        })  
                    }
                </Box>

                <Box className='save-button-class'>
                    <Button className='save-button'>Save</Button>
                </Box>
            </Box>
        </Box>
    )
}

export default SettingsPageBody