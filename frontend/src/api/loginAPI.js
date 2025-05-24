import axios from 'axios';
import { loginStart, loginSuccess, loginFailure, setToken } from '../redux/authSlice';

export const loginUser = async (user, dispatch, navigate) => {
    dispatch(loginStart());
    try {
        const res = await axios.post(`http://localhost:8080/signin`, user);
        dispatch(loginSuccess(res.data));
        // Save token
        dispatch(setToken(res.data.token));
        navigate('/home');
    } catch (err) {
        dispatch(loginFailure());
    }
}
