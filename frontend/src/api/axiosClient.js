// File này dùng để cấu hình axios client
// Sau đây là một ví dụ về cách cấu hình axios client trong React để gửi yêu cầu đến API.
// const res = await axiosClient.get('/profile');
// chỉ cần gọi axiosClient.get('/profile') là đủ

import axios from 'axios';
import store from '../redux/store'; // Đường dẫn tới file store của bạn

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
});

// Gắn token nếu có
axiosClient.interceptors.request.use((config) => {
    const token = store.getState().auth.token; // Lấy token từ Redux store
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default axiosClient;