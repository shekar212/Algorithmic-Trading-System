import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {

    localStorage.removeItem(environment.tokenKey);
    localStorage.removeItem(environment.usernameKey);

    setTimeout(() => 
    {
      this.router.navigate([`/`]);
    },
   1000);
  }

}
