import React, { useState, useEffect } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import axiosClient from '../../../api/axiosClient';
import './paySection.css'

function PaySection() {
    const location = useLocation();
    const navigate = useNavigate();
    const [orderItems, setOrderItems] = useState(location.state?.items || []);
    const [shippingInfo, setShippingInfo] = useState({
        phone: '',
        address: ''
    });

    // Fetch thông tin người dùng khi component mount
    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await axiosClient.get('/NguoiDungHienTai');
                setShippingInfo({
                    phone: response.data.soDienThoai || '',
                    address: response.data.diaChi || ''
                });
            } catch (error) {
                console.error('Lỗi khi lấy thông tin người dùng:', error);
            }
        };
        fetchUserInfo();
    }, []);

    const handleSubmitOrder = async () => {
        try {
            // Tách thành mảng riêng để đảm bảo thứ tự
            const dsspArr = orderItems.map(item => item.id);
            const slspArr = orderItems.map(item => item.soLuong);

            // Tạo FormData và thêm theo đúng thứ tự
            const formData = new FormData();

            // Thêm các dssp trước
            dsspArr.forEach(id => {
                formData.append('dssp', id);
            });

            // Sau đó thêm các slsp
            slspArr.forEach(soLuong => {
                formData.append('slsp', soLuong);
            });

            // Cuối cùng thêm thông tin giao hàng
            formData.append('so_dien_thoai', shippingInfo.phone.replace(/[^0-9]/g, ''));
            formData.append('dia_chi', shippingInfo.address);

            // Log để kiểm tra thứ tự
            for (let pair of formData.entries()) {
                console.log(pair[0], ':', pair[1]);
            }

            await axiosClient.post('/dat_hang', formData);
            alert('Đặt hàng thành công!');
            navigate('/orders');
        } catch (error) {
            console.error('Chi tiết lỗi:', error.response?.data || error.message);
            alert('Đặt hàng thất bại! Vui lòng thử lại');
        }
    };

    const handleQuantityChange = (index, change) => {
        setOrderItems(prev => prev.map((item, idx) => {
            if (idx === index) {
                const newQuantity = Math.max(1, item.soLuong + change);
                return { ...item, soLuong: newQuantity };
            }
            return item;
        }));
    };

    const calculateTotal = () => {
        return orderItems.reduce((sum, item) => sum + (item.gia * item.soLuong), 0);
    };

    return (
        <>
            <div className="pay-section">
                {/* Địa chỉ nhận hàng */}
                <div className="shipping-info">
                    <div className="shipping-icon">📍</div>
                    <div className="shipping-details">
                        <p className="label">Địa Chỉ Nhận Hàng</p>
                        <input
                            type="text"
                            className="shipping-phone"
                            placeholder="Nhập số điện thoại"
                            value={shippingInfo.phone}
                            onChange={(e) => setShippingInfo({ ...shippingInfo, phone: e.target.value })}
                        />
                        <input
                            type="text"
                            className="shipping-address"
                            placeholder="Nhập địa chỉ nhận hàng"
                            value={shippingInfo.address}
                            onChange={(e) => setShippingInfo({ ...shippingInfo, address: e.target.value })}
                        />
                    </div>

                </div>

                <div className="pay-header">
                    <h2>Thanh Toán</h2>
                    <p>Vui lòng kiểm tra thông tin đơn hàng trước khi thanh toán</p>
                </div>

                <div className="pay-content">
                    <div className="product-table">

                        <table>
                            <thead>
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th>Đơn giá</th>
                                    <th>Số lượng</th>
                                    <th>Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                {orderItems.map((item, index) => (
                                    <tr key={item.id}>
                                        <td className="product-info">
                                            <img src={item.sourceHinhAnh} alt={item.tenSanPham} />
                                            <div className="product-details">

                                                <div className="product-name">{item.tenSanPham}</div>
                                            </div>
                                        </td>
                                        <td>{item.gia?.toLocaleString()}₫</td>
                                        <td>
                                            <div className="quantity-control">
                                                <button
                                                    className="quantity-btn"
                                                    onClick={() => handleQuantityChange(index, -1)}
                                                >
                                                    -
                                                </button>
                                                <span className="quantity-display">{item.soLuong}</span>
                                                <button
                                                    className="quantity-btn"
                                                    onClick={() => handleQuantityChange(index, 1)}
                                                >
                                                    +
                                                </button>
                                            </div>
                                        </td>
                                        <td>{(item.gia * item.soLuong).toLocaleString()}₫</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    <div className="pay-summary">
                        <span>Tổng thanh toán: </span>
                        <span className="pay-total">{calculateTotal().toLocaleString()}₫</span>
                    </div>
                    <div className="pay-methods">
                        <h3>Phương Thức Thanh Toán</h3>
                        <label className='pay-method'><input type="radio" defaultChecked name="method" /> Thanh toán khi nhận hàng</label><br />
                        {/* <label className='pay-method'><input type="radio" name="method" /> Thẻ tín dụng/Ghi nợ</label><br />
                        <label className='pay-method'><input type="radio" name="method" /> Ví điện tử</label> */}
                    </div>
                </div>



                <button
                    className="btn btn-primary pay-btn"
                    onClick={handleSubmitOrder}
                >
                    Đặt Hàng
                </button>
            </div>
        </>
    )
}

export default PaySection