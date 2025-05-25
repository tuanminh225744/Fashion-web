import React, { useState, useEffect } from 'react';
import axiosClient from '../../../api/axiosClient';
import './orderCard.css';

const OrderCard = ({ orderId, trangThaiDonHang, nhanHang }) => {
    const [orderDetails, setOrderDetails] = useState(null);
    const [productDetails, setProductDetails] = useState([]);
    const [discounts, setDiscounts] = useState({});

    const getOrderStatus = () => {
        if (nhanHang) return 'HOAN_THANH';
        if (!trangThaiDonHang) return 'DA_HUY';
        return 'CHO_GIAO_HANG';
    };

    // Fetch order details and discounts
    useEffect(() => {
        const fetchOrderAndDiscounts = async () => {
            try {
                const orderResponse = await axiosClient.get(`/findByDonHangId?id=${orderId}`);
                setOrderDetails(orderResponse.data);

                const productPromises = orderResponse.data.map(item =>
                    axiosClient.get(`/chi_tiet_san_pham?id=${item.key.sanPhamID}`)
                );

                const productsData = await Promise.all(productPromises);
                const products = productsData.map(response => response.data);

                // Fetch discount codes for each product's category
                const discountPromises = products.map(product =>
                    axiosClient.get(`/maDuocChon?danhmuc_id=${product.danhMucID}`)
                );

                const discountResponses = await Promise.all(discountPromises);
                const discountMap = {};
                discountResponses.forEach((response, index) => {
                    if (response.data) {
                        discountMap[products[index].danhMucID] = response.data;
                    }
                });

                setDiscounts(discountMap);
                setProductDetails(products);
            } catch (error) {
                console.error('Lỗi khi lấy thông tin:', error);
            }
        };
        fetchOrderAndDiscounts();
    }, [orderId]);

    const calculateDiscountedPrice = (price, danhMucID) => {
        const discount = discounts[danhMucID];
        if (discount && new Date(discount.ngayHetHan) > new Date() && discount.soLuotConLai > 0) {
            return price * (1 - discount.phanTramGiamGia / 100);
        }
        return price;
    };

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
                                <span className="original-price">{product.gia.toLocaleString()}₫</span>
                                {discounts[product.danhMucID] && (
                                    <div className="discount-info">
                                        <span className="discount-code">Mã: {discounts[product.danhMucID].ma}</span>
                                        <span className="discount-percent">-{discounts[product.danhMucID].phanTramGiamGia}%</span>
                                    </div>
                                )}
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
                        {productDetails.reduce((total, product, index) => {
                            const quantity = orderDetails[index]?.soLuong || 1;
                            const discountedPrice = calculateDiscountedPrice(product.gia, product.danhMucID);
                            return total + (discountedPrice * quantity);
                        }, 0).toLocaleString()}₫
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
