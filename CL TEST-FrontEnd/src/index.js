import _ from 'lodash';
import React, { Component } from 'react'; 
import ReactDOM from 'react-dom'; 
import YTSearch from 'youtube-api-search';
import VideoList from './components/videoList';
import SearchBar from './components/searchBar';
import VideoDetail from './components/videoDetail';
import NavBar from './components/navBar'


const API_KEY = 'AIzaSyASSJNgzHQQDmhotVtZZwUbDGibRw7OQCc';


class App extends Component {
    constructor(props){
        super(props);

        this.state = {videos: [],
        selectedVideo:null
        };

        this.videoSearch('Funny fails');
        
    }

    videoSearch(term){

        YTSearch({key: API_KEY, term: term, maxResults: 6},(videos)  => {
            this.setState({
                videos: videos,
                selectedVideo:videos[0]
            }), {relatedToVideoId: video.etag} 
        });

    }

    render() {
        const videoSearch = _.debounce((term) => {this.videoSearch(term)}, 300);
    
        return (
        <div className= "card-width">
                    <div class="header">
                                <NavBar siteTitle='YouTaped' />
                                <br/>
                                <SearchBar onSearchTermChange={videoSearch}/>
                    </div>   
                    <br/>    
                    <div className="card"> 
                                <form className="card-header">
                                    <VideoDetail video={this.state.selectedVideo} />
                                </form>
                                <br/>
                                <form className="card-header">
                                    <h5>
                                        Recommended Videos
                                    </h5>
                                </form>
                                <form className="card-body">
                                    <VideoList
                                    onVideoSelect={selectedVideo => this.setState({selectedVideo}) }
                                    videos={this.state.videos} />
                                </form>     
                    </div>   
        </div> 
        );
      }
}


ReactDOM.render(<App />, document.querySelector('.container'));


