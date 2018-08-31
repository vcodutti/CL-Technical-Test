import React from 'react';

const VideoDetail = ({video}) => {
    
  if (!video) {
    return <div class="videodescription">Wait for Load</div>;
  }

  const videoId = video.id.videoId;
  const url = `https://www.youtube.com/embed/${videoId}`;

  return (
    <div>
        <div className="embed-responsive embed-responsive-16by9">
          <iframe className="embed-responsive-item" src={url} ></iframe>
        </div>
        <div class="videotitle">
            {video.snippet.title}
        </div>
        <div class="videodescription">
            {video.snippet.description}
        </div>
    </div>
  );
};

export default VideoDetail;
