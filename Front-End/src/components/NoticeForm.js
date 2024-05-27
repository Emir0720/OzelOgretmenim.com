import React, { useState, useContext } from 'react';
import styled from './NoticeForm.module.css';
import { Context } from '../context/Context';
import axios from 'axios';

function getBasicAuthHeader(username, password) {
  const credentials = `${username}:${password}`;
  const encodedCredentials = btoa(credentials); // btoa() encodes to base64
  return `Basic ${encodedCredentials}`;
}

const NoticeForm = () => {
  const { user, setAppointment } = useContext(Context);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const notice = {
    title: title,
    description: description,
    price: price,
  };
  const handleAppointment = () => {
    setAppointment(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Burada ilan oluşturma işlemleri yapılabilir

    const headers = {
        'Authorization': getBasicAuthHeader(user.username, user.password),
        'Content-Type': 'application/json',
      };
    axios.post('http://localhost:8080/notice/create',
        notice,
        {
          headers: headers
        }
      ).then(response => {
        console.log('Notice response:', response.data);
      }).catch(error => {
        console.error('Notice failed:', error);
      })

    // try {
    //   const headers = {
    //     'Authorization': getBasicAuthHeader(username, password),
    //     'Content-Type': 'application/json',
    //   };
    //   const response = await axios.post(
    //     'http://localhost:8080/notice/create',
    //     notice,
    //     {
    //       headers: headers,
    //     }
    //   );

    //   console.log('Notice response:', response.data);
    // } catch (error) {
    //   console.error('Appointment failed:', error);
    // }
  };
  return (
    <div className={styled['main-container']} onSubmit={handleSubmit}>
      <form className={styled['form-container']}>
        <div className={styled['title']}>
          <label htmlFor='title'>Başlık</label>
          <input
            type='text'
            id='title'
            name='title'
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder='Lütfen dersiniz için bir başlık girin.'
            required
          />
        </div>
        <div className={styled.description}>
          <label htmlFor='description'>Açıklama</label>
          <textarea
            id='description'
            name='description'
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder='İlanınızda gözükecek bilgileri yazın...'
            required
          />
        </div>
        <div className={styled.price}>
          <label htmlFor='price'>Fiyat</label>
          <input
            type='text'
            id='price'
            name='price'
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            placeholder='Lütfen saatlik ücretinizi girin.'
            required
          />
        </div>
        <div className={styled['btn-container']}>
          <button type='submit' className={styled['submit-btn']}>
            Gönder
          </button>
          <button onClick={handleAppointment} className={styled['submit-btn']}>
            Geri Dön
          </button>
        </div>
      </form>
    </div>
  );
};

export default NoticeForm;
