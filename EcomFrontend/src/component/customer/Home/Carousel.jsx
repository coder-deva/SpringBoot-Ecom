import { useState } from 'react';
import Carousel from 'react-bootstrap/Carousel';
import carousel1 from '../../../assets/ExampleCarouselImage/carousel1.jpg';
import carousel2 from '../../../assets/ExampleCarouselImage/carousel2.jpg';
import carousel3 from '../../../assets/ExampleCarouselImage/carousel3.jpg';
import React from 'react';

function Carousels() {
  const [index, setIndex] = useState(0);

  const handleSelect = (selectedIndex) => {
    setIndex(selectedIndex);
  };

  return (
    <div className="mt-12">
      <Carousel
        activeIndex={index}
        onSelect={handleSelect}
        interval={3000} 
        fade
        pause={false}   
        controls={true} 
        indicators={true} 
      >
        <Carousel.Item>
          <img src={carousel1} alt="First slide" className="d-block w-100 rounded" />
          <Carousel.Caption>
            <h3>First slide label</h3>
            <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
          </Carousel.Caption>
        </Carousel.Item>

        <Carousel.Item>
          <img src={carousel2} alt="Second slide" className="d-block w-100 rounded" />
          <Carousel.Caption>
            <h3>Second slide label</h3>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
          </Carousel.Caption>
        </Carousel.Item>

        <Carousel.Item>
          <img src={carousel3} alt="Third slide" className="d-block w-100 rounded" />
          <Carousel.Caption>
            <h3>Third slide label</h3>
            <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
          </Carousel.Caption>
        </Carousel.Item>
      </Carousel>
    </div>
  );
}

export default Carousels;
