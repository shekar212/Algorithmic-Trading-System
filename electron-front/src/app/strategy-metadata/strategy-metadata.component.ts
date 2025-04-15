import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { header } from '../http-header/auth-header';
import { environment } from '../environments/environment';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'app-strategy-metadata',
  templateUrl: './strategy-metadata.component.html',
  styleUrls: ['./strategy-metadata.component.css']
})
export class StrategyMetadataComponent {
  stocks=1
  cycles = 1
  StartTime=""
  EndTime=""
  list:any[]=[]
  symbol=""
  symbolId = 0
  term=""
  interval:string=""
  strategyId : number
  errorMessage = ""
  
  targetPercent:number=3
  stopLoss:number=1.5

  constructor(private http: HttpClient, private route: ActivatedRoute,private router:Router) {
    this.strategyId=Number(this.route.snapshot.paramMap.get('strategyId'));
  }
  fetchList(input: string){
    this.http.get<any[]>(`${environment.urlForSymbol}/${input}`,{
      headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${localStorage.getItem('token')}`)
  }).subscribe((data) => {
    this.list = data;
  });
  }

  setSymbol(input: string, id:number){
    this.symbol = input;
    this.symbolId = id;
    this.list = [];
  }

  submitMeta(){
    this.http.post<any>(environment.urlForSavingMetadata+`/${this.strategyId}`,
      {
        stocks : this.stocks,
        cycles : this.cycles,
        startTime : this.StartTime,
        endTime : this.EndTime,
        symbolId : this.symbolId,
        interval: this.interval,
        strategyId : this.strategyId,
        targetPercent : this.targetPercent,
        stopLoss : this.stopLoss
    },{
      headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${localStorage.getItem('token')}`)
  }).subscribe(
      {
        next: data => {
          if(data.status == `saved`){
            if(this.strategyId==1)
            this.router.navigate([`/subscribe/strategy-ma/${data.id}`]);
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
