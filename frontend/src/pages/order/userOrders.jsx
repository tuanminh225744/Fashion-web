import React, { useState, useEffect } from 'react';
import OrderCard from './orderCard/orderCard';
import axiosClient from '../../api/axiosClient';
import './userOrders.css';
import Header from '../../components/layout/header/header';
import Footer from '../../components/layout/footer/footer';
import UserSidebar from './userSidebar/userSidebar';

const UserOrders = () => {
    const [orders, setOrders] = useState([]);
    const [activeTab, setActiveTab] = useState('Tất cả');

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await axiosClient.get('/danhsachhientai');
                setOrders(response.data || []);
            } catch (error) {
                console.error('Lỗi khi lấy danh sách đơn hàng:', error);
                setOrders([]);
            }
        };
        fetchOrders();
    }, []);

    const getFilteredOrders = () => {
        if (activeTab === 'Tất cả') return orders;

        return orders.filter(order => {
            switch (activeTab) {
                case 'Chờ giao hàng':
                    return order.trangThaiDonHang && !order.nhanHang;
                case 'Hoàn thành':
                    return order.nhanHang;
                case 'Đã hủy':
                    return !order.trangThaiDonHang;
                default:
                    return true;
            }
        });
    };

    return (
        <>
            <Header />
            <div className="app__container">
                <div className="grid">
                    <div className="grid__row user-section">
                        <UserSidebar />
                        <div className="grid__column-10 order-page-container">
                            <div className="order-tabs">
                                {['Tất cả', 'Chờ giao hàng', 'Hoàn thành', 'Đã hủy'].map((label) => (
                                    <div
                                        key={label}
                                        className={`order-tabs__item ${label === activeTab ? 'order-tabs__item--active' : ''}`}
                                        onClick={() => setActiveTab(label)}
                                    >
                                        {label}
                                    </div>
                                ))}
                            </div>

                            {getFilteredOrders().map((order) => (
                                <OrderCard
                                    key={order.id}
                                    orderId={order.id}
                                    trangThaiDonHang={order.trangThaiDonHang}
                                    nhanHang={order.nhanHang}
                                />
                            ))}
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </>
    );
};

export default UserOrders;