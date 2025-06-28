import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {
  MDBContainer, MDBRow, MDBCol, MDBCard, MDBCardBody,
  MDBCardImage, MDBCardText, MDBTypography, MDBIcon, MDBBtn
} from 'mdb-react-ui-kit';

export default function CustomerProfile() {
  const [customer, setCustomer] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [form, setForm] = useState({ name: '', email: '', phone: '' });
  const [imageFile, setImageFile] = useState(null);

  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        const res = await axios.get('http://localhost:8080/api/customer/profile', {
          headers: { Authorization: 'Bearer ' + localStorage.getItem('token') },
        });
        setCustomer(res.data);
        setForm({
          name: res.data.name || '',
          email: res.data.email || '',
          phone: res.data.phone || '',
        });
      } catch (err) {
        console.error('Fetch failed:', err);
      }
    };
    fetchCustomer();
  }, []);

  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleFileChange = (e) => {
    setImageFile(e.target.files[0]);
  };

  const handleUpdate = async () => {
    const formData = new FormData();
    formData.append('name', form.name);
    formData.append('email', form.email);
    formData.append('phone', form.phone);
    if (imageFile) formData.append('file', imageFile);

    try {
      const res = await axios.put(
        `http://localhost:8080/api/customer/update/${customer.id}`,
        formData,
        {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'multipart/form-data',
          },
        }
      );
      alert('Profile updated successfully');
      setCustomer(res.data);
      setEditMode(false);
      setImageFile(null);
    } catch (err) {
      console.error('Update error:', err);
      alert('Update failed');
    }
  };

  return (
    <section className="vh-100" style={{ backgroundColor: '#f4f5f7' }}>
      <MDBContainer className="py-5 h-100">
        <MDBRow className="justify-content-center align-items-center h-100">
          <MDBCol lg="10">
            <MDBCard className="mb-3" style={{ borderRadius: '.5rem', padding: '2rem' }}>
              <MDBRow className="g-0">
                <MDBCol
                  md="4"
                  className="text-center text-white"
                  style={{
                    backgroundColor: '#4b5563',
                    borderTopLeftRadius: '.5rem',
                    borderBottomLeftRadius: '.5rem',
                  }}
                >
                  <MDBCardImage
                    src={
                      imageFile
                        ? URL.createObjectURL(imageFile)
                        : `/images/${customer?.profilePic || 'default.png'}`
                    }
                    alt="Profile"
                    className="my-4"
                    style={{ width: '120px', borderRadius: '50%' }}
                    fluid
                  />
                  <MDBTypography tag="h5">{customer?.name}</MDBTypography>
                  <MDBCardText>Customer</MDBCardText>

                  <MDBBtn size="sm" color="light" className="mb-4" style={{ width: '120px' }} onClick={() => setEditMode(!editMode)}>
                    <MDBIcon far icon="edit" className="me-2" />
                    {editMode ? 'Cancel' : 'Edit'}
                  </MDBBtn>
                </MDBCol>

                <MDBCol md="8">
                  <MDBCardBody className="p-4">
                    <MDBTypography tag="h6">Customer Info</MDBTypography>
                    <hr className="mt-0 mb-4" />

                    <MDBRow className="pt-1">
                      <MDBCol size="6" className="mb-3">
                        <MDBTypography tag="h6">Username</MDBTypography>
                        <MDBCardText className="text-muted">{customer?.user?.username}</MDBCardText>
                      </MDBCol>

                      <MDBCol size="6" className="mb-3">
                        <MDBTypography tag="h6">Name</MDBTypography>
                        {editMode ? (
                          <input
                            type="text"
                            name="name"
                            value={form.name}
                            className="form-control"
                            onChange={handleInputChange}
                          />
                        ) : (
                          <MDBCardText className="text-muted">{customer?.name}</MDBCardText>
                        )}
                      </MDBCol>
                    </MDBRow>

                    <MDBRow>
                      <MDBCol size="6" className="mb-3">
                        <MDBTypography tag="h6">Email</MDBTypography>
                        {editMode ? (
                          <input
                            type="email"
                            name="email"
                            value={form.email}
                            className="form-control"
                            onChange={handleInputChange}
                          />
                        ) : (
                          <MDBCardText className="text-muted">{customer?.email}</MDBCardText>
                        )}
                      </MDBCol>

                      <MDBCol size="6" className="mb-3">
                        <MDBTypography tag="h6">Phone</MDBTypography>
                        {editMode ? (
                          <input
                            type="text"
                            name="phone"
                            value={form.phone}
                            className="form-control"
                            onChange={handleInputChange}
                          />
                        ) : (
                          <MDBCardText className="text-muted">{customer?.phone}</MDBCardText>
                        )}
                      </MDBCol>
                    </MDBRow>

                    {editMode && (
                      <MDBRow className="mb-3">
                        <MDBCol>
                          <MDBTypography tag="h6">Upload Profile Picture</MDBTypography>
                          <input
                            type="file"
                            accept="image/*"
                            className="form-control"
                            onChange={handleFileChange}
                          />
                        </MDBCol>
                      </MDBRow>
                    )}

                    {editMode && (
                      <MDBBtn color="primary" onClick={handleUpdate}>
                        Save Changes
                      </MDBBtn>
                    )}
                  </MDBCardBody>
                </MDBCol>
              </MDBRow>
            </MDBCard>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </section>
  );
}
