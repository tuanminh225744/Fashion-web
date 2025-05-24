import React, { useEffect, useState } from 'react';
import './userSidebar.css';
import axiosClient from '../../../api/axiosClient';
import { useNavigate } from 'react-router-dom'; // Thay đổi import

const UserSidebar = () => {
    const [userInfo, setUserInfo] = useState({
        username: '',
        avatar: ''
    });
    const navigate = useNavigate(); // Thêm useNavigate hook

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await axiosClient.get('/NguoiDungHienTai');
                setUserInfo({
                    username: response.data.username || '',
                    avatar: response.data.sourceImage || 'assets/img/anh_user/default-user.jpg'
                });
            } catch (error) {
                console.error('Lỗi khi lấy thông tin người dùng:', error);
            }
        };
        fetchUserInfo();
    }, []);

    return (
        <div className="grid__column-2 sidebar">
            <div className="sidebar-header">
                <img src={userInfo.avatar} alt="Avatar" className="avatar avatar-small" />
                <p>{userInfo.username}</p>
            </div>
            <ul>
                <li
                    className={`sidebar-btn `}
                    onClick={() => navigate('/profile')}
                >
                    Tài Khoản Của Tôi
                </li>
                <li
                    className={`sidebar-btn`}
                    onClick={() => navigate('/orders')}
                >
                    Đơn Mua
                </li>
                <li
                    className={`sidebar-btn sidebar-btn-active`}

                >
                    Đổi Mật Khẩu
                </li>
            </ul>
        </div>
    );
};

export default UserSidebar;