import React, { useEffect, useState } from 'react';
import CartItem from './cartItem/cartItem';
import './cartSection.css';
import axiosClient from '../../../api/axiosClient';

const CartSection = () => {
    const [cartItems, setCartItems] = useState([]);
    const [checked, setChecked] = useState([]);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axiosClient.get('/GioHang');
                const items = response.data.map(item => ({
                    ...item,
                    soLuong: item.soLuong || 1
                }));
                setCartItems(items);
                setChecked(new Array(items.length).fill(false));
            } catch (error) {
                setCartItems([]);
                setChecked([]);
            }
        };
        fetchProducts();
    }, []);

    const handleQuantityChange = (index, newQuantity) => {
        setCartItems(prev =>
            prev.map((item, i) =>
                i === index ? { ...item, soLuong: newQuantity } : item
            )
        );
    };

    const handleCheckChange = (index) => {
        setChecked(prev =>
            prev.map((v, i) => (i === index ? !v : v))
        );
    };

    const handleCheckAll = (e) => {
        setChecked(new Array(cartItems.length).fill(e.target.checked));
    };

    const handleDeleteSelected = async () => {
        try {
            // Lọc ra các sản phẩm đã chọn
            const selectedItems = cartItems.filter((_, index) => checked[index]);

            // Xóa từng sản phẩm đã chọn
            for (const item of selectedItems) {
                await axiosClient.post(`xoa_khoi_gio_hang?san_phamid=${item.id}`);
            }

            // Refresh lại danh sách
            const response = await axiosClient.get('/GioHang');
            setCartItems(response.data.map(item => ({
                ...item,
                soLuong: item.soLuong || 1
            })));
            setChecked(new Array(response.data.length).fill(false));
        } catch (error) {
            console.error('Lỗi khi xóa sản phẩm:', error);
            alert('Xóa sản phẩm thất bại!');
        }
    };

    const handleDeleteItem = async (itemId) => {
        try {
            await axiosClient.post(`xoa_khoi_gio_hang?san_phamid=${itemId}`);
            // Refresh lại danh sách sau khi xóa
            const response = await axiosClient.get('/GioHang');
            setCartItems(response.data.map(item => ({
                ...item,
                soLuong: item.soLuong || 1
            })));
            setChecked(new Array(response.data.length).fill(false));
        } catch (error) {
            console.error('Lỗi khi xóa sản phẩm:', error);
            alert('Xóa sản phẩm thất bại!');
        }
    };

    // Tính tổng tiền và số sản phẩm đã chọn
    const totalChecked = checked.filter(Boolean).length;
    const totalPrice = cartItems.reduce((sum, item, idx) => {
        if (checked[idx]) {
            const giaHienTai = item.giaHienTai || item.gia || 0;
            const soLuong = item.soLuong || 1;
            return sum + giaHienTai * soLuong;
        }
        return sum;
    }, 0);

    return (
        <div className="app__container">
            <div className="grid">
                <div className="grid__row cart">
                    <div className="cart__header">
                        <div>
                            <input
                                className="cart__checkbox-all"
                                type="checkbox"
                                checked={checked.length > 0 && checked.every(Boolean)}
                                onChange={handleCheckAll}
                            />
                        </div>
                        <div className="cart__column">Sản Phẩm</div>
                        <div className="cart__column">Đơn Giá</div>
                        <div className="cart__column">Số Lượng</div>
                        <div className="cart__column">Số Tiền</div>
                        <div className="cart__column">Thao Tác</div>
                    </div>

                    {cartItems.map((item, index) => (
                        <CartItem
                            key={index}
                            product={item}
                            quantity={item.soLuong}
                            onQuantityChange={newQuantity => handleQuantityChange(index, newQuantity)}
                            checked={checked[index] || false}
                            onCheckChange={() => handleCheckChange(index)}
                            onDelete={() => handleDeleteItem(item.id)}
                        />
                    ))}

                    <div className="cart__footer">
                        <div className="cart__select-all">
                            <input
                                className="cart__checkbox-all"
                                type="checkbox"
                                checked={checked.length > 0 && checked.every(Boolean)}
                                onChange={handleCheckAll}
                            />
                            <span>Chọn Tất Cả ({totalChecked})</span>
                            <button
                                className="cart__delete"
                                onClick={handleDeleteSelected}
                                disabled={totalChecked === 0}
                            >
                                Xóa
                            </button>
                        </div>

                        <div className="cart__checkout-group">
                            <div className="cart__summary">
                                <span className="cart__total-price--text">Tổng cộng ({totalChecked} Sản phẩm):</span>
                                <span className="cart__total-price">{totalPrice.toLocaleString()}₫</span>
                            </div>
                            <button className="cart__checkout btn btn-primary">Mua Hàng</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CartSection;