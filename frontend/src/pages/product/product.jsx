import React from 'react'
import Footer from '../../components/layout/footer/footer'
import ProductCart from './productCart/productCart';
import Header from '../../components/layout/header/header';
import { useLocation } from 'react-router-dom';

const Product = () => {
    const location = useLocation();
    const product = location.state?.product;

    return (
        <>
            <Header />
            <ProductCart product={product} />
            <Footer />
        </>
    );
};

export default Product;