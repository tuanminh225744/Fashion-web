import React, { useEffect, useState } from "react";
import './productSection.css';
import axiosClient from "../../../api/axiosClient";
import { useNavigate } from "react-router-dom";

const categories = [
    { label: "Tất cả", type: 0 },
    { label: "Áo nam", type: 1 },
    { label: "Áo nữ", type: 2 },
    { label: "Quần nam", type: 3 },
    { label: "Quần nữ", type: 4 },
    { label: "Đầm & Váy", type: 5 },
    { label: "Đồ thể thao", type: 6 },
    { label: "Đồ ngủ & Mặc nhà", type: 7 },
    { label: "Phụ kiện", type: 8 },
    { label: "Giày dép", type: 9 }
];

const priceSortOptions = [
    { label: "Mặc định", value: "" },
    { label: "Thấp đến cao", value: "asc" },
    { label: "Cao đến thấp", value: "desc" },
];

const PAGE_SIZE = 10;

const ProductSection = () => {
    const [allProducts, setAllProducts] = useState([]);
    const [selectedType, setSelectedType] = useState(0);
    const [priceSort, setPriceSort] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axiosClient.get('/Allsp');
                setAllProducts(response.data);
            } catch (error) {
                setAllProducts([]);
            }
        };
        fetchProducts();
    }, []);

    useEffect(() => {
        setCurrentPage(1);
    }, [selectedType, priceSort]);

    // Lọc sản phẩm theo danh mục
    let filteredProducts = allProducts;
    if (selectedType !== 0) {
        filteredProducts = allProducts.filter(
            (product) => {
                return Number(product.danhMucID) === Number(selectedType);
            }
        );
    }

    // Sắp xếp theo giá nếu có chọn
    if (priceSort === "asc") {
        filteredProducts = [...filteredProducts].sort((a, b) => (a.gia || 0) - (b.gia || 0));
    } else if (priceSort === "desc") {
        filteredProducts = [...filteredProducts].sort((a, b) => (b.gia || 0) - (a.gia || 0));
    }

    const totalProducts = filteredProducts.length;
    const totalPages = Math.ceil(totalProducts / PAGE_SIZE);
    const pagedProducts = filteredProducts.slice((currentPage - 1) * PAGE_SIZE, currentPage * PAGE_SIZE);

    return (
        <div className="app__container">
            <div className="grid">
                <div className="grid__row product-section">
                    <div className="grid__column-2 category">
                        <nav className="category">
                            <h3 className="category-heading">
                                <i className="category-heading-icon fa-solid fa-bars"></i>
                                Danh mục
                            </h3>
                            <ul className="category-list">
                                {categories.map((cat) => (
                                    <li
                                        key={cat.type}
                                        className={
                                            "category-item" +
                                            (selectedType === cat.type ? " category-item--active" : "")
                                        }
                                        onClick={() => setSelectedType(cat.type)}
                                    >
                                        <span className="category-item-link">{cat.label}</span>
                                    </li>
                                ))}
                            </ul>
                        </nav>
                    </div>
                    <div className="grid__column-10 products">
                        <div className="home-filter">
                            <span className="home-filter__label">Sắp xếp theo giá</span>
                            <div className="select-input">
                                <span className="select-input__label">
                                    {priceSortOptions.find(opt => opt.value === priceSort)?.label || "Giá"}
                                </span>
                                <i className="select-input__icon fa-solid fa-angle-down"></i>
                                <ul className="select-input__list">
                                    {priceSortOptions.map(opt => (
                                        <li
                                            className="select-input__item"
                                            key={opt.value}
                                            onClick={() => setPriceSort(opt.value)}
                                        >
                                            <span className="select-input__link">{opt.label}</span>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                        <div className="home-product">
                            <div className="grid__row">
                                {pagedProducts.map((product) => {
                                    const saleOff = product.saleOffPrecent || 0;
                                    const price = product.gia || 0;
                                    const priceBeforeSale = saleOff > 0
                                        ? Math.round(price / (1 - saleOff / 100))
                                        : price;
                                    return (
                                        <div className="grid__column-2-4" key={product.id}>
                                            <div
                                                className="home-product-item"
                                                style={{ cursor: "pointer" }}
                                                onClick={() => navigate('/product', { state: { product } })}
                                            >
                                                <div
                                                    className="home-product-item__img"
                                                    style={{ backgroundImage: `url(${product.sourceHinhAnh})` }}
                                                ></div>
                                                <h4 className="home-product-item__name">
                                                    {product.tenSanPham}
                                                </h4>
                                                <span className="home-product-item__price">
                                                    {saleOff > 0 && (
                                                        <span className="home-product-item__price-old">
                                                            {priceBeforeSale.toLocaleString()}₫
                                                        </span>
                                                    )}
                                                    <span className="home-product-item__price-current">
                                                        {price.toLocaleString()}₫
                                                    </span>
                                                </span>
                                                {saleOff > 0 && (
                                                    <div className="home-product-item__sale-off">
                                                        <span className="home-product-item__sale-off-persent">
                                                            {saleOff}%
                                                        </span>
                                                        <span className="home-product-item__sale-off-label">GIẢM</span>
                                                    </div>
                                                )}
                                            </div>
                                        </div>
                                    );
                                })}
                            </div>
                        </div>
                        <ul className="pagination home-product__pagination">
                            <li className={`pagination-item${currentPage === 1 ? " pagination-item--disabled" : ""}`}>
                                <button
                                    className="pagination-item__link"
                                    onClick={() => setCurrentPage(currentPage - 1)}
                                    disabled={currentPage === 1}
                                >
                                    <i className="pagination-item__icon fa-solid fa-angle-left"></i>
                                </button>
                            </li>
                            {Array.from({ length: totalPages }, (_, idx) => (
                                <li
                                    key={idx + 1}
                                    className={`pagination-item${currentPage === idx + 1 ? " pagination-item--active" : ""}`}
                                >
                                    <button
                                        className="pagination-item__link"
                                        onClick={() => setCurrentPage(idx + 1)}
                                    >
                                        {idx + 1}
                                    </button>
                                </li>
                            ))}
                            <li className={`pagination-item${currentPage === totalPages || totalPages === 0 ? " pagination-item--disabled" : ""}`}>
                                <button
                                    className="pagination-item__link"
                                    onClick={() => setCurrentPage(currentPage + 1)}
                                    disabled={currentPage === totalPages || totalPages === 0}
                                >
                                    <i className="pagination-item__icon fa-solid fa-angle-right"></i>
                                </button>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProductSection;