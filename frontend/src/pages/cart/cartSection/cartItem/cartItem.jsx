import React from 'react';
import './cartItem.css';

const CartItem = ({ product, quantity, onQuantityChange, checked, onCheckChange, onDelete }) => {
    const giaHienTai = product.gia || 0;
    const tongTien = giaHienTai * quantity;

    const handleDecrease = () => {
        if (quantity > 1) onQuantityChange(quantity - 1);
    };
    const handleIncrease = () => {
        onQuantityChange(quantity + 1);
    };
    const handleInputChange = (e) => {
        let value = parseInt(e.target.value, 10);
        if (isNaN(value) || value < 1) value = 1;
        onQuantityChange(value);
    };

    return (
        <div className="cart__item">
            <div className="cart__checkbox">
                <input
                    type="checkbox"
                    checked={checked}
                    onChange={onCheckChange}
                />
            </div>
            <div className="cart__product">
                <img
                    className="cart__image"
                    src={product.sourceHinhAnh}
                    alt={product.tenSanPham}
                />
                <div className="cart__details">
                    <div className="cart__title">{product.tenSanPham}</div>
                </div>
            </div>
            <div className="cart__price">
                <span className="cart__price-old">
                    {product.gia && product.gia !== giaHienTai ? product.gia.toLocaleString() + '₫' : ''}
                </span>
                <span className="cart__price-current">
                    {giaHienTai ? giaHienTai.toLocaleString() + '₫' : ''}
                </span>
            </div>
            <div className="cart__quantity">
                <button className="cart__btn" onClick={handleDecrease}>-</button>
                <input
                    className="cart__input"
                    type="number"
                    min="1"
                    value={quantity}
                    onChange={handleInputChange}
                />
                <button className="cart__btn" onClick={handleIncrease}>+</button>
            </div>
            <div className="cart__total">
                {tongTien ? tongTien.toLocaleString() + '₫' : ''}
            </div>
            <div className="cart__action">
                <button
                    className="cart__delete-btn"
                    onClick={() => onDelete(product.id)}
                >
                    Xóa
                </button>
            </div>
        </div>
    );
};

export default CartItem;