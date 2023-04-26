export const localhostURL: string = 'http://localhost:8080';


export const RegisterRequest = async (objectBody: object) => {
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(objectBody)
    }

    const serverResponse = await fetch(`${localhostURL}/api/v1/auth/signup`, options)
    const serverResponseToJSON = await serverResponse.json();

    return serverResponseToJSON;
}

export const LoginRequest = async(objectBody: object) => {
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(objectBody)
    }
    
    const serverResponse = await fetch(`${localhostURL}/api/v1/auth/signin`, options)
    const serverResponseToJSON = await serverResponse.json();

    return serverResponseToJSON;
}

export const CreatePostFunction = async (objectBody: object) => {
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(objectBody)
    }

    const serverResponse = await fetch(`${localhostURL}/api/v1/posts`, options)
    const serverResponseToJSON = await serverResponse.json();

    return serverResponseToJSON;
}