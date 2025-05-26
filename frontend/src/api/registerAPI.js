import axiosClient from './axiosClient';
import { registerStart, registerSuccess, registerFailure } from '../redux/authSlice';

export const registerUser = async (user, dispatch, navigate) => {
    dispatch(registerStart());
    try {
        const res = await axiosClient.post(`http://localhost:8080/dang_ky`, {
            ten: user.email,
            matKhau: user.password
        });
        dispatch(registerSuccess());
        // Navigate to verify page with email
        navigate('/verifySignIn', { state: { email: user.email } });
    } catch (error) {
        dispatch(registerFailure());
        alert('Đăng ký thất bại!');
    }
};