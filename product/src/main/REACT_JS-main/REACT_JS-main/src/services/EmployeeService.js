import axios from 'axios'

const BASE_URL = 'http://localhost:8080/api/v1/product';

class EmployeeService{
    getAllImages() {
        return axios.get(BASE_URL);
    }

    createEmployee(employee){
        return axios.post(BASE_URL+'/upload', employee);
    }
    getEmployeeById(employeeId){
        return axios.get(BASE_URL + '/' + employeeId);
    }

    updateEmployee(employeeId, employee){
        return axios.put(BASE_URL + '/' +employeeId, employee);
    }

    deleteEmployee(employeeId){
        return axios.delete(BASE_URL + '/' + employeeId);
    }
}

export default new EmployeeService();