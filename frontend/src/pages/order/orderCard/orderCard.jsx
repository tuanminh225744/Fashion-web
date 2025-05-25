import React, { useState, useEffect } from 'react';
import axiosClient from '../../../api/axiosClient';
import './orderCard.css';

const OrderCard = ({ orderId, trangThaiDonHang, nhanHang }) => {
    const [orderDetails, setOrderDetails] = useState(null);
    const [productDetails, setProductDetails] = useState([]);

    const getOrderStatus = () => {
        if (nhanHang) return 'HOAN_THANH';
        if (!trangThaiDonHang) return 'DA_HUY';
        return 'CHO_GIAO_HANG';
    };

    // Fetch order details first
    useEffect(() => {
        const fetchOrderDetails = async () => {
            try {
                const response = await axiosClient.get(`/findByDonHangId?id=${orderId}`);
                setOrderDetails(response.data);

                // Sau khi có order details, fetch chi tiết từng sản phẩm
                const productPromises = response.data.map(item =>
                    axiosClient.get(`/chi_tiet_san_pham?id=${item.key.sanPhamID}`)
                );

                const productsData = await Promise.all(productPromises);
                const products = productsData.map((response, index) => ({
                    ...response.data,
                    soLuong: response.data[index]?.soLuong
                }));

                setProductDetails(products);
            } catch (error) {
                console.error('Lỗi khi lấy chi tiết đơn hàng:', error);
            }
        };
        fetchOrderDetails();
    }, [orderId]);

    if (!orderDetails || !productDetails.length) return null;

    const getStatusText = (status) => {
        switch (status) {
            case 'CHO_GIAO_HANG': return 'CHỜ GIAO HÀNG';
            case 'HOAN_THANH': return 'HOÀN THÀNH';
            case 'DA_HUY': return 'ĐÃ HỦY';
            default: return status;
        }
    };

    const handleCancelOrder = async () => {
        try {
            await axiosClient.post(`/huydonhang?don_hangid=${orderId}`);
            alert('Đã hủy đơn hàng thành công');
            window.location.reload();
        } catch (error) {
            console.error('Lỗi khi hủy đơn hàng:', error);
            alert('Hủy đơn hàng thất bại');
        }
    };


    const handleConfirmOrder = async () => {
        try {
            await axiosClient.post(`/xacNhanNhanHang?id=${orderId}`);
            alert('Xác nhận đã nhận hàng thành công');
            window.location.reload();
        } catch (error) {
            console.error('Lỗi khi xác nhận đơn hàng:', error);
            alert('Xác nhận đơn hàng thất bại');
        }
    };

    return (
        <div className="order-card">
            <div className="order-card__header">
                <span className={`order-card__status ${getOrderStatus().toLowerCase()}`}>
                    {getStatusText(getOrderStatus())}
                </span>
            </div>

            {productDetails.map((product, index) => (
                <div key={product.id} className="order-card__body">
                    <img
                        className="order-card__image"
                        src={product.sourceHinhAnh}
                        alt={product.tenSanPham}
                    />
                    <div className="order-card__details">
                        <h4 className="order-card__title">{product.tenSanPham}</h4>
                        <div className="order-card__meta">
                            <div className="order-card__price">
                                {product.gia.toLocaleString()}₫
                            </div>
                        </div>
                        <div className="order-card__quantity">
                            <span className="order-card__quantity-label">Số lượng:</span>
                            <span className="order-card__quantity-value">
                                {orderDetails[index]?.soLuong || 1}
                            </span>
                        </div>
                    </div>
                </div>
            ))}

            <div className="order-card__footer">
                <div className="order-card__total">
                    Thành tiền: <span>
                        {productDetails.reduce((total, product, index) =>
                            total + (product.gia * (orderDetails[index]?.soLuong || 1)), 0
                        ).toLocaleString()}₫
                    </span>
                </div>
                {trangThaiDonHang && !nhanHang && (
                    <div className="order-card__actions">
                        <button
                            className="btn btn-primary"
                            onClick={handleConfirmOrder}
                        >
                            Đã nhận hàng
                        </button>
                        <button
                            className="btn btn-danger"
                            onClick={handleCancelOrder}
                        >
                            Hủy đơn hàng
                        </button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default OrderCard;
