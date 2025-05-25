import React from 'react'
import Footer from '../../components/layout/footer/footer'
import ProductCard from './productCart/productCard';
import Header from '../../components/layout/header/header';
import { useLocation } from 'react-router-dom';

const Product = () => {
    const location = useLocation();
    const product = location.state?.product;

    return (
        <>
            <Header />
            <ProductCard product={product} />
            <Footer />
        </>
    );
};

export default Product;