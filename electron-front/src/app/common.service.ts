import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from './environments/environment';
import { EmailPassword } from './models/EmailPassword';
import { header } from '../app/http-header/auth-header';
@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private http:HttpClient) { }

  authenticate(credentials:EmailPassword):Observable<any>{
  
    return this.http.post<any>(environment.urlForAuth,credentials);
  }

  fetchStrategies(token:any):Observable<any[]>{
    return this.http.get<any[]>(environment.urlForStrategyList,{
      headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${token}`)
  });
  }

  fetchUserStrategies(token:any):Observable<any[]>{
    return this.http.get<any[]>(environment.urlForUserStrategy,{
      headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${token}`)
  });
  }


}
