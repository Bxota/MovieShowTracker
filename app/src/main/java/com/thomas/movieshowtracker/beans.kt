package com.thomas.movieshowtracker

// Search Shows

public data class SearchShowsBean(var shows :ArrayList<PosterBean>, var total :Int)

public data class ShowsInformationBean(var show :IdShowBean)

public data class IdShowBean(var images :ImageBean, var title :String, var creation :String, var platforms :PlatformsBean, var description :String, var notes :NoteBean)

public data class PlatformsBean(var svods :List<SvodsBean>)

public data class ImageBean(var banner :String, var poster :String)


// Search Movies

public data class SearchMoviesBean(var movies :ArrayList<PosterBean>, var total :Int)

public data class MoviesInformationBean(var movie :IdMovieBean)

public data class IdMovieBean(var backdrop :String, var title: String, var release_date :String, var platform_links :List<PlatformsShowsBean>, var synopsis :String, var notes :NoteBean)

public data class PlatformsShowsBean(var platform :String)


// All Bean

public data class PosterBean(var poster: String, var title: String, var id :Int)

public data class SvodsBean(var name :String, var logo :String)

public data class NoteBean(var mean :Double)

public data class CastingShowsBean(var characters :List<CharactersBean>)

public data class CharactersBean(var name :String, var picture :String)