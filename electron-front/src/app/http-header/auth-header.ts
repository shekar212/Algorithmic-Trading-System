import { environment } from "../environments/environment";
import {HttpHeaders} from '@angular/common/http';

//get token
let token = localStorage.getItem('token');

export var header = {
    headers: new HttpHeaders()
    .set('Authorization',  `Bearer ${token}`)
};
