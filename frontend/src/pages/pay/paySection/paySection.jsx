import React from 'react'
import './paySection.css'

function PaySection() {
    return (
        <>
            <div className="pay-section">
                {/* Địa chỉ nhận hàng */}
                <div className="shipping-info">
                    <div className="shipping-icon">📍</div>
                    <div className="shipping-details">
                        <p className="label">Địa Chỉ Nhận Hàng</p>
                        <p><strong>Phạm Tuấn Minh</strong> (+84) 367393126</p>
                        <p>Tòa KTX B6 Trường Đại Học Bách Khoa Hà Nội, Ngõ 30 Tạ Quang Bửu, Phường Bách Khoa, Quận Hai Bà Trưng, Hà Nội</p>
                    </div>
                    <div className="shipping-actions">
                        <span className="default-tag">Mặc Định</span>
                        <a href="#" className="change-link">Thay Đổi</a>
                    </div>
                </div>

                <div className="pay-header">
                    <h2>Thanh Toán</h2>
                    <p>Vui lòng kiểm tra thông tin đơn hàng trước khi thanh toán</p>
                </div>

                <div className="pay-content">
                    <div className="product-table">
                        <h3>Sản phẩm</h3>
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
                                <tr>
                                    <td className="product-info">
                                        <img src="https://via.placeholder.com/60" alt="Sản phẩm" />
                                        <div className="product-details">

                                            <div className="product-name">Dép Bánh Mì Nam Nữ YZ 3 Màu Basic Vân Nh...</div>
                                            <div className="product-type">Loại: 999 Be, 42</div>
                                        </div>
                                    </td>
                                    <td>₫50.000</td>
                                    <td>1</td>
                                    <td>₫50.000</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div className="pay-methods">
                        <h3>Phương Thức Thanh Toán</h3>
                        <label><input type="radio" name="method" /> Thanh toán khi nhận hàng</label><br />
                        <label><input type="radio" name="method" /> Thẻ tín dụng/Ghi nợ</label><br />
                        <label><input type="radio" name="method" /> Ví điện tử</label>
                    </div>
                </div>

                <button className="btn btn-primary pay-btn">Thanh Toán Ngay</button>
            </div>
        </>
    )
}

export default PaySection