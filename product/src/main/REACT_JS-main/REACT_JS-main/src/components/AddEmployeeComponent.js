import React, { useState, useEffect } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import EmployeeService from "../services/EmployeeService";
import axios from "axios";
const AddEmployeeComponent = () => {
  const handleFileUpload = (event) => {
    // get the selected file from the input
    const imageFileName = event.target.files[0];
    // create a new FormData object and append the file to it
    const formData = new FormData();
    formData.append("file", imageFileName);
    formData.append("title", title);
    formData.append("description", description);
    formData.append("title", title);
    const employee = { title, description, imageFileName };
    // make a POST request to the File Upload API with the FormData object and Rapid API headers
    axios
      .post("http://localhost:8080/api/v1/product/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          "x-rapidapi-host": "file-upload8.p.rapidapi.com",
          "x-rapidapi-key": "your-rapidapi-key-here",
        },
      })
      .then((response) => {
        // handle the response
        console.log(response);
        return alert("Success Upload!");
      })
      .catch((error) => {
        // handle errors

        console.log(error);
      
      });
  };

  const [title, setTitle] = useState("");
  const [imageFileNam, setimageFileName] = useState([]);
  const [description, setDescription] = useState("");
   const onFileChange = event => {
 
    // Update the state
    this.setimageFileName({ selectedFile: event.target.files[0] });

};
  const history = useHistory();
  const { id } = useParams();

  const saveOrUpdateEmployee = (e) => {
    e.preventDefault();

    const employee = { title, description, imageFileNam };

    if (id) {
      EmployeeService.updateEmployee(id, employee)
        .then((response) => {
          history.push("/employees");
        })
        .catch((error) => {
          console.log(error);
        });
    } else {
      EmployeeService.createEmployee(employee, {
        headers: {"Access-Control-Allow-Origin": "*",
          "Content-Type": "multipart/form-data",
          "x-rapidapi-host": "file-upload8.p.rapidapi.com",
          "x-rapidapi-key": "your-rapidapi-key-here",
        },
      })
        .then((response) => {
          console.log(response.data);

          history.push("/employees");
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  useEffect(() => {
    EmployeeService.getEmployeeById(id)
      .then((response) => {
        setTitle(response.data.title);
        setimageFileName(response.data.imageFileName);
        setDescription(response.data.description);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const titles = () => {
    if (id) {
      return <h2 className="text-center">Update Employee</h2>;
    } else {
      return <h2 className="text-center">Add Employee</h2>;
    }
  };

  return (
    <div>
      <br />
      <br />
      <div className="container">
        <div className="row">
          <div className="card col-md-6 offset-md-3 offset-md-3">
            {titles()}
            <div className="card-body">
              <form>
                <div className="form-group mb-2">
                  <label className="form-label"> Title :</label>
                  <input
                    type="text"
                    placeholder="Enter Title"
                    name="title"
                    className="form-control"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  ></input>
                </div>

                <div className = "form-group mb-2">
                                    <label className = "form-label"> Image :</label>
                                    <input
                                        type="file"
                                       
                                        name="file"
                                        onChange = {onFileChange}
                                    />
                                    
                                </div>

                <div className="form-group mb-2">
                  <label className="form-label"> description :</label>
                  <input
                    type="text"
                    placeholder="Enter description"
                    name="description"
                    className="form-control"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                  ></input>
                </div>

              
                <button
                  className="btn btn-success"
                  onClick={(e) => saveOrUpdateEmployee(e)}
                >
                  Submit{" "}
                </button>
                <Link to="/employees" className="btn btn-danger">
                  {" "}
                  Cancel{" "}
                </Link>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddEmployeeComponent;
