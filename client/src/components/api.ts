/*@dev I made two variables that fetches username from localstorage
 because in Next.js there is a problem with: 'localStorage is not defined'
 just like window problem.
*/
import Cookie from 'universal-cookie';

export const localhostURL: string = 'https://society-production.up.railway.app';
const cookies: Cookie = new Cookie();
const userCookie: string | undefined = cookies.get('accessToken');

export const RegisterRequest = async (objectBody: any) => {
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(objectBody)
    }

    try {
        const serverResponse = await fetch(`${localhostURL}/api/v1/auth/signup`, options)
        const serverResponseToJSON = await serverResponse.json();

        return serverResponseToJSON;
    } catch(error: any) {
        console.error(error);
    }
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

    try {
        const serverResponse = await fetch(`${localhostURL}/api/v1/auth/signin`, options)
        const serverResponseToJSON = await serverResponse.json();

        return serverResponseToJSON;
    } catch(error: any) {
        console.error(error);
    }
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

export const getAllComments = async (postId: number) => {
    const usernameFromLocalStorage: string | null = localStorage.getItem('username');

    const getCommentsRequest = await fetch(`${localhostURL}/api/v1/posts/comments?postId=${postId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${userCookie}`,
            'X-username': `${usernameFromLocalStorage}`,
        }
    });
    
    try {
        const convertResponseToJSON = await getCommentsRequest.json();

        return convertResponseToJSON;
    } catch(error){
        console.error(error);
    }
}

export const postCommentFunction = async (postCommentObject: any, postId: number) => {
    const usernameFromLocalStorage: string | null = localStorage.getItem('username');

    const postCommentRequest = await fetch(`${localhostURL}/api/v1/posts/comments?postId=${postId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-username': `${usernameFromLocalStorage}`,
            'Authorization': `Bearer ${userCookie}`
        },
        body: JSON.stringify(postCommentObject)
    })

    try {
        const postCommentToJSON = await postCommentRequest.json();

        return postCommentToJSON;
    } catch(error) {
        console.error(error)
    }
}

export const editCommentFunction = async (commentId: number, putObject: any) => {
    const usernameFromLocalStorage: string | null = localStorage.getItem('username');

    const editCommentRequest = await fetch(`${localhostURL}/api/v1/posts/comments/${commentId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'X-username': `${usernameFromLocalStorage}`,
            'Authorization': `Bearer ${userCookie}`
        },
        body: JSON.stringify(putObject)
    })

    try {
        const responseToEditCommentRequest = await editCommentRequest.json();

        return responseToEditCommentRequest;
    } catch(error) {
        console.error(error);
    }
}

export const deleteCommentFunction = async (commentId: number) => {
    const usernameFromLocalStorage: string | null = localStorage.getItem('username');

    const deleteCommentRequest = await fetch(`${localhostURL}/api/v1/posts/comments/${commentId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'X-username': `${usernameFromLocalStorage}`,
            'Authorization': `Bearer ${userCookie}`
        },
    })

    try {
        const responseToDeleteCommentRequest = await deleteCommentRequest.json();
        console.log(deleteCommentRequest)

        return responseToDeleteCommentRequest;
    } catch(error) {
        console.error(error);
    }
}