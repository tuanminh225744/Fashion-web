import React, { useState, useEffect } from 'react';
import './changePassword.css';
import Header from '../../components/layout/header/header';
import Footer from '../../components/layout/footer/footer';
import UserSidebar from './userSidebar/userSidebar';
import axiosClient from '../../api/axiosClient';

const ChangePassword = () => {
    const [email, setEmail] = useState('');
    const [verificationCode, setVerificationCode] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [codeSent, setCodeSent] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchUserEmail = async () => {
            try {
                const response = await axiosClient.get('/NguoiDungHienTai');
                setEmail(response.data.ten || '');
            } catch (error) {
                console.error('Lỗi khi lấy email:', error);
            }
        };
        fetchUserEmail();
    }, []);

    const handleSendCode = async () => {
        try {
            await axiosClient.post(`/reset_password?email=${email}`);
            setCodeSent(true);
            alert('Mã xác nhận đã được gửi!');
        } catch (error) {
            setError('Lỗi khi gửi mã xác nhận');
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (newPassword !== confirmPassword) {
            setError('Mật khẩu xác nhận không khớp');
            return;
        }
        try {
            await axiosClient.post(`/confirm_reset_password?email=${email}&mxn=${verificationCode}&new_pass=${newPassword}`);
            alert('Đổi mật khẩu thành công!');
            window.location.href = '/profile';
        } catch (error) {
            setError('Lỗi khi đổi mật khẩu');
        }
    };

    return (
        <>
            <Header />
            <div className="app__container">
                <div className="grid">
                    <div className="grid__row user-section">
                        <UserSidebar />
                        <div className="grid__column-10 profile-content">
                            <div className="profile-header">
                                <h2>Đổi Mật Khẩu</h2>
                                <p>Vui lòng xác nhận email và nhập mật khẩu mới</p>
                            </div>

                            <div className="form-container">
                                <form onSubmit={handleSubmit} className="form-left">
                                    {error && <div className="error-message">{error}</div>}

                                    <div className="form-group">
                                        <button
                                            type="button"
                                            className="btn btn-secondary"
                                            onClick={handleSendCode}
                                            disabled={codeSent}
                                        >
                                            {codeSent ? 'Đã gửi mã' : 'Gửi mã xác nhận'}
                                        </button>
                                    </div>

                                    <div className="form-group">
                                        <label>Mã xác nhận</label>
                                        <input
                                            type="text"
                                            placeholder="Nhập mã xác nhận"
                                            value={verificationCode}
                                            onChange={(e) => setVerificationCode(e.target.value)}
                                            required
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label>Mật khẩu mới</label>
                                        <input
                                            type="password"
                                            placeholder="Nhập mật khẩu mới"
                                            value={newPassword}
                                            onChange={(e) => setNewPassword(e.target.value)}
                                            required
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label>Xác nhận mật khẩu</label>
                                        <input
                                            type="password"
                                            placeholder="Nhập lại mật khẩu mới"
                                            value={confirmPassword}
                                            onChange={(e) => setConfirmPassword(e.target.value)}
                                            required
                                        />
                                    </div>

                                    <div className="form-group">
                                        <button type="submit" className="save-btn btn btn-primary">
                                            Đổi mật khẩu
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </>
    );
};

export default ChangePassword;