/*@dev I made two variables that fetches username from localstorage
 because in Next.js there is a problem with: 'localStorage is not defined'
 just like window problem.
*/
import Cookie from 'universal-cookie';

export const localhostURL: string = 'http://localhost:8080';
const cookies: Cookie = new Cookie();
export const userCookie: string | undefined = cookies.get('accessToken');

export const RegisterRequest = async (objectBody: any) => {
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

export const LoginRequest = async (objectBody: any) => {
    localStorage.setItem('username', objectBody.usernameOrEmail);

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

export const CreatePostFunction = async (objectBody: any) => {
    const usernameFromLocalStorage: string | null = localStorage.getItem('username');

    if (userCookie === undefined || usernameFromLocalStorage === null) {
        return;
    }

    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${userCookie}`,
            'X-username': `${usernameFromLocalStorage}`
        },
        body: JSON.stringify(objectBody)
    }

    const serverResponse = await fetch(`${localhostURL}/api/v1/posts`, options)
    const serverResponseToJSON = await serverResponse.json();

    return serverResponseToJSON;
}

export const getAllPosts = async () => {
    const serverResponse = await fetch(`${localhostURL}/api/v1/posts`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${userCookie}`
        }
    });

    try {
        const serverResponseToJSON = await serverResponse.json();
        return serverResponseToJSON;
    } catch(error) {
        console.error(error)
    }
}

export const editPostFunction = async (id: number, dataToEdit: any) => {
    const usernameFromLocalStorage: string | null = localStorage.getItem('username');

    const editPostRequest = await fetch(`${localhostURL}/api/v1/posts/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'X-username': `${usernameFromLocalStorage}`,
            'Authorization': `Bearer ${userCookie}`
        },
        body: JSON.stringify(dataToEdit)
    })
    const convertResponseDataToJSON = await editPostRequest.json();

    return convertResponseDataToJSON;
}

export const deletePostFunction = async (id: number) => {
    const usernameFromLocalStorage: string | null = localStorage.getItem('username');

    const deletePostRequest = await fetch(`${localhostURL}/api/v1/posts/${JSON.stringify(id)}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'X-username': `${usernameFromLocalStorage}`,
            'Authorization': `Bearer ${userCookie}`
        }
    });
    const convertResponseDataToJSON = await deletePostRequest.json();

    return convertResponseDataToJSON;
}