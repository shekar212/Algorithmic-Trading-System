import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { interval } from 'rxjs';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-strategy-ma',
  templateUrl: './strategy-ma.component.html',
  styleUrls: ['./strategy-ma.component.css']
})



export class StrategyMaComponent {
 
  mas:number=2
  masList:any[]
  entryCond:string[]
  dataId : number
  errorMessage=""

  constructor(private http: HttpClient, private route: ActivatedRoute,private router:Router) {
    this.masList=[]
    this.entryCond = []
    this.dataId=Number(this.route.snapshot.paramMap.get('dataId'));

  }

  ngOnInit(): void {
   
  }

  add(){
    this.masList=[]
    for (let i = 0; i < this.mas; i++) {
      this.masList.push({value:1,condition:''});
    }
    
  }


  submitData(){
   

  this.http.post<any>(environment.urlForMaStrategy+`/${this.dataId}`,
      {
       avgs : this.masList
    },{
      headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${localStorage.getItem('token')}`)
  }).subscribe(
      {
        next: data => {
          if(data.status == `saved`){
            
            this.router.navigate([`/my-strategies`]);
          }
           
          else
          this.errorMessage = "error";
            
          },
        error: error => {
          this.router.navigate([`/logout`]);
        }
    }
     );
  }
}
