import React, { useState, useEffect } from 'react';
import './userProfile.css';
import axiosClient from '../../../../api/axiosClient';

const UserProfile = () => {
    const [userInfo, setUserInfo] = useState({
        username: '',
        address: '',
        email: '',
        phoneNumber: '',
        gender: 'male',
        avatar: ''
    });

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await axiosClient.get('/NguoiDungHienTai');
                setUserInfo({
                    username: response.data.username || '',
                    address: response.data.diaChi || '',
                    email: response.data.ten || '',
                    phoneNumber: response.data.soDienThoai || '',
                    gender: response.data.gioiTinh || 'male',
                    avatar: response.data.sourceImage || 'assets/img/anh_user/default-user.jpg'
                });
            } catch (error) {
                console.error('Lỗi khi lấy thông tin người dùng:', error);
            }
        };
        fetchUserInfo();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axiosClient.post('/updateUser', {
                username: userInfo.username,
                ten: userInfo.email,
                diaChi: userInfo.address,
                soDienThoai: userInfo.phoneNumber,
                gioiTinh: userInfo.gender
            });
            alert('Cập nhật thông tin thành công!');
            window.location.reload(); // Thêm dòng này để refresh trang
        } catch (error) {
            alert('Cập nhật thông tin thất bại!');
            console.error(error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUserInfo(prev => ({
            ...prev,
            [name]: value
        }));
    };

    return (
        <div className="grid__column-10 profile-content">
            <div className="profile-header">
                <h2>Hồ Sơ Của Tôi</h2>
                <p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
            </div>

            <div className="form-container">
                <form onSubmit={handleSubmit} className="form-left">
                    <div className="form-group">
                        <label>Tên</label>
                        <input
                            type="text"
                            name="username"
                            value={userInfo.username}
                            onChange={handleChange}
                            placeholder="Nhập tên của bạn"
                        />
                    </div>
                    <div className="form-group">
                        <label>Địa chỉ</label>
                        <input
                            type="text"
                            name="address"
                            value={userInfo.address}
                            onChange={handleChange}
                            placeholder="Nhập địa chỉ của bạn"
                        />
                    </div>
                    <div className="form-group">
                        <label>Email</label>
                        <input
                            type="text"
                            value={userInfo.email}
                            placeholder="Nhập email của bạn"
                            readOnly
                        />
                    </div>
                    <div className="form-group">
                        <label>Số điện thoại</label>
                        <input
                            type="text"
                            name="phoneNumber"
                            value={userInfo.phoneNumber}
                            onChange={handleChange}
                            placeholder="Nhập số điện thoại của bạn"
                        />
                    </div>
                    <div className="form-group">
                        <label>Giới tính</label>
                        <div>
                            <input
                                type="radio"
                                name="gender"
                                value="male"
                                checked={userInfo.gender === 'male'}
                                onChange={handleChange}
                            />
                            <p>Nam</p>
                        </div>
                        <div>
                            <input
                                type="radio"
                                name="gender"
                                value="female"
                                checked={userInfo.gender === 'female'}
                                onChange={handleChange}
                            />
                            <p>Nữ</p>
                        </div>
                    </div>

                    <div className="form-group">
                        <button type="submit" className="btn btn-primary save-btn">Lưu</button>
                    </div>
                </form>


            </div>
        </div>
    );
};

export default UserProfile;