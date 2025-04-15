import { Component, OnInit } from '@angular/core';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.css']
})
export class UserHeaderComponent implements OnInit {
  
  userName;

  constructor() {
    this.userName=localStorage.getItem(environment.usernameKey);
    
   }

  ngOnInit(): void {
  }

}
