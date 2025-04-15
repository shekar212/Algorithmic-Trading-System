import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../environments/environment';
import { CommonService } from '../common.service';
import { EmailPassword } from '../models/EmailPassword';
 
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email="";
  password="";

  errorMessage="";

  constructor(private loginService:CommonService,private router:Router) {
   }

  ngOnInit(): void {
  }

  

  authenticateAndSaveToStorage(credential:EmailPassword): void {
    
    this.loginService.authenticate(credential)
        .subscribe(
          {
            next: data => {
                localStorage.setItem("token", data.jwt);
                localStorage.setItem(environment.usernameKey,data.name);

                setTimeout(() => 
                {
                  this.router.navigate([`/discover`]);
                },
                3000);

                
              },
            error: error => {
                this.errorMessage = error.message;
                
            }
        }
         );
    
      }

  submitCredentials(){
    
    let cred={email:this.email,password:this.password};
    this.authenticateAndSaveToStorage(cred);
  }

}
