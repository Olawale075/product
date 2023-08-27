import Card from "react-bootstrap/Card";
import CardGroup from "react-bootstrap/CardGroup";
import React, { useState, useEffect } from "react";
import EmployeeService from "../services/EmployeeService";
import { Col, Container, ListGroup, Row } from "react-bootstrap";
import { FaWhatsappSquare } from "react-icons/fa";
const ListEmployeeComponent = () => {
  let navigate = useNavigate();
  const [imageData, setImageData] = useState([]);
  const [time, setTime] = useState("");
  useEffect(() => {
    getAllEmployees();
  }, []);
  const getAllEmployees = () => {
    EmployeeService.getAllImages()
      .then((response) => {
        setImageData(response.data);
        setTime(response.data.createDateTime.data);
        console.log(response.data);
        console.log(response.data.createDateTime.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const element=[...imageData].reverse().map((employee,idx) => (
        
             
  

    <Col   className="mr-1 mb-2" key={employee.id}>
                
                <Card md-3 style={{height:"100%", width:"11rem"}} >
            <Card.Img 
              className=""
              minBreakpoint="sm"
              variant="top"
              src={`http://localhost:8080/api/v1/product/${employee.id}/image/download`}
              alt=""
            />
            <p>Item: {employee.title}</p>
   
          </Card>
              </Col>
            
          )
          
        )
        return (
          <div>
            <Container>  <FaWhatsappSquare style={{width:"10rem"}}/>
              <Row>
                {element}  
              </Row>  
            </Container>       
            <div><FaWhatsappSquare/></div>        
          </div>
  );
};

export default ListEmployeeComponent;
