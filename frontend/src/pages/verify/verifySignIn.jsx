import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axiosClient from '../../api/axiosClient';

const VerifySignIn = () => {
    const [verificationCode, setVerificationCode] = useState('');
    const location = useLocation();
    const navigate = useNavigate();
    const email = location.state?.email;

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axiosClient.post('/xac_nhan_dang_ky', {
                ten: email,
                maXacNhan: verificationCode
            });
            alert('Xác nhận đăng ký thành công!');
            navigate('/login');
        } catch (error) {
            alert('Mã xác nhận không đúng!');
        }
    };

    return (
        <div
            style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                minHeight: '100vh',
                backgroundColor: 'var(--primary-color)',
            }}
        >
            <div
                style={{
                    width: '400px',
                    backgroundColor: '#fff',
                    borderRadius: '5px',
                    padding: '32px',
                    boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
                }}
            >
                <div style={{ textAlign: 'center', marginBottom: '32px' }}>
                    <h3 style={{ fontSize: '24px', color: '#333', margin: 0 }}>Xác nhận đăng ký</h3>
                </div>
                <form onSubmit={handleSubmit}>
                    <div style={{ marginBottom: '24px' }}>
                        <input
                            type="text"
                            placeholder="Nhập mã xác nhận"
                            value={verificationCode}
                            onChange={(e) => setVerificationCode(e.target.value)}
                            style={{
                                width: '100%',
                                height: '40px',
                                padding: '8px 12px',
                                border: '1px solid #dbdbdb',
                                borderRadius: '2px',
                                fontSize: '16px',
                                outline: 'none',
                            }}
                            onFocus={(e) => (e.target.style.borderColor = '#ee4d2d')}
                            onBlur={(e) => (e.target.style.borderColor = '#dbdbdb')}
                        />
                    </div>
                    <div style={{ textAlign: 'center' }}>
                        <button
                            type="submit"
                            style={{
                                minWidth: '142px',
                                height: '36px',
                                backgroundColor: '#ee4d2d',
                                border: 'none',
                                borderRadius: '2px',
                                color: 'white',
                                fontSize: '14px',
                                cursor: 'pointer',
                            }}
                            onMouseOver={(e) => (e.target.style.opacity = 0.9)}
                            onMouseOut={(e) => (e.target.style.opacity = 1)}
                        >
                            XÁC NHẬN
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default VerifySignIn;
