package com.jirwindev.popularmovies.themoviedb.objects;

/**
 * Created by josh on 9/16/15.
 */
public class Discover {

	int     page;
	Movie[] results;

	public Discover(int page, Movie[] results) {
		this.page = page;
		this.results = results;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Movie[] getResults() {
		return results;
	}

	public void setResults(Movie[] results) {
		this.results = results;
	}

	/*
	public class Movie {
		boolean adult;
		String  backdrop_path;
		int[]   genre_ids;
		int     id;
		String  original_language;
		String  original_title;
		String  overview;
		String  release_date;
		String  poster_path;
		double  popularity;
		String  title;
		boolean video;
		double  vote_average;
		int     vote_count;
		Uri     poster;

		public Movie(boolean adult, String backdrop_path, int[] genre_ids, int id,
					 String original_language, String original_title, String overview, String release_date, String poster_path, double popularity, String title, boolean video, double vote_average, int vote_count) {
			this.adult = adult;
			this.backdrop_path = backdrop_path;
			this.genre_ids = genre_ids;
			this.id = id;
			this.original_language = original_language;
			this.original_title = original_title;
			this.overview = overview;
			this.release_date = release_date;
			this.poster_path = poster_path;
			this.popularity = popularity;
			this.title = title;
			this.video = video;
			this.vote_average = vote_average;
			this.vote_count = vote_count;
		}

		public boolean isAdult() {
			return adult;
		}

		public void setAdult(boolean adult) {
			this.adult = adult;
		}

		public String getBackdrop_path() {
			return backdrop_path;
		}

		public void setBackdrop_path(String backdrop_path) {
			this.backdrop_path = backdrop_path;
		}

		public int[] getGenre_ids() {
			return genre_ids;
		}

		public void setGenre_ids(int[] genre_ids) {
			this.genre_ids = genre_ids;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getOriginal_language() {
			return original_language;
		}

		public void setOriginal_language(String original_language) {
			this.original_language = original_language;
		}

		public String getOriginal_title() {
			return original_title;
		}

		public void setOriginal_title(String original_title) {
			this.original_title = original_title;
		}

		public String getOverview() {
			return overview;
		}

		public void setOverview(String overview) {
			this.overview = overview;
		}

		public String getRelease_date() {
			return release_date;
		}

		public void setRelease_date(String release_date) {
			this.release_date = release_date;
		}

		public String getPoster_path() {
			return poster_path;
		}

		public void setPoster_path(String poster_path) {
			this.poster_path = poster_path;
		}

		public double getPopularity() {
			return popularity;
		}

		public void setPopularity(double popularity) {
			this.popularity = popularity;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public boolean isVideo() {
			return video;
		}

		public void setVideo(boolean video) {
			this.video = video;
		}

		public double getVote_average() {
			return vote_average;
		}

		public void setVote_average(double vote_average) {
			this.vote_average = vote_average;
		}

		public int getVote_count() {
			return vote_count;
		}

		public void setVote_count(int vote_count) {
			this.vote_count = vote_count;
		}

		public Uri getPoster() {
			return poster;
		}

		public void setPoster(Uri poster) {
			this.poster = poster;
		}


		class PosterDownloadThread extends AsyncTask<URL, Void, Uri> {

			private ImageView imageView;

			public PosterDownloadThread(ImageView imageView) {
				this.imageView = imageView;
			}

			@Override
			protected Uri doInBackground(URL... urls) {
				Log.e(getClass().getSimpleName(), "running...");

				if (urls.length != 1)
					return null;
				URL url = urls[0];

				//Get Storage Directory
				String root = Environment.getExternalStorageDirectory().toString();
				File storageDir = new File(root + "/posters");
				Log.wtf(getClass().getSimpleName(), "Creating storage dirs...");
				boolean makeDirs = storageDir.mkdirs();
				if (makeDirs)
					Log.wtf(getClass().getSimpleName(), "Created storages dirs...");
				else
					Log.wtf(getClass().getSimpleName(), "Couldn't create storages dirs...");

				//Save poster to file
				String fname = url.getFile();
				File file = new File(storageDir, fname);

				if (!file.exists()) {
					try {
						//Download bitmap
						Bitmap poster = null;
						InputStream in = url.openStream();
						poster = BitmapFactory.decodeStream(in);

						//Save to file
						FileOutputStream out = new FileOutputStream(file);
						poster.compress(Bitmap.CompressFormat.JPEG, 90, out);
						out.flush();
						out.close();
					}
					catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}

				return Uri.fromFile(file);
			}

			@Override
			protected void onPostExecute(Uri p) {
				super.onPostExecute(p);

				poster = p;

//				ImageView imageView = ((ImageView) gridMovies.getChildAt(position - gridMovies.getFirstVisiblePosition()));
//				if (position <= gridMovies.getLastVisiblePosition() && imageView != null) {
				Log.e("IMAGE", "SET AND INVALIDATED");
				imageView.setImageURI(p);
				imageView.invalidate();
//				}
			}
		}
	}
	*/

	/*
	 {
	 "page":1,
	 "results":[
	 {
	 "adult":false,
	 "backdrop_path":"\/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg",
	 "genre_ids":[
	 53,
	 28,
	 12
	 ],
	 "id":76341,
	 "original_language":"en",
	 "original_title":"Mad Max: Fury Road",
	 "overview":"An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There's Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.",
	 "release_date":"2015-05-15",
	 "poster_path":"\/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
	 "popularity":47.218736,
	 "title":"Mad Max: Fury Road",
	 "video":false,
	 "vote_average":7.6,
	 "vote_count":2283
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/rFtsE7Lhlc2jRWF7SRAU0fvrveQ.jpg",
	 "genre_ids":[
	 878,
	 28,
	 12
	 ],
	 "id":99861,
	 "original_language":"en",
	 "original_title":"Avengers: Age of Ultron",
	 "overview":"When Tony Stark tries to jumpstart a dormant peacekeeping program, things go awry and Earth’s Mightiest Heroes are put to the ultimate test as the fate of the planet hangs in the balance. As the villainous Ultron emerges, it is up to The Avengers to stop him from enacting his terrible plans, and soon uneasy alliances and unexpected action pave the way for an epic and unique global adventure.",
	 "release_date":"2015-05-01",
	 "poster_path":"\/t90Y3G8UGQp0f0DrP60wRu9gfrH.jpg",
	 "popularity":31.818343,
	 "title":"Avengers: Age of Ultron",
	 "video":false,
	 "vote_average":7.6,
	 "vote_count":2219
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/dkMD5qlogeRMiEixC4YNPUvax2T.jpg",
	 "genre_ids":[
	 28,
	 12,
	 878,
	 53
	 ],
	 "id":135397,
	 "original_language":"en",
	 "original_title":"Jurassic World",
	 "overview":"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.",
	 "release_date":"2015-06-12",
	 "poster_path":"\/uXZYawqUsChGSj54wcuBtEdUJbh.jpg",
	 "popularity":27.683676,
	 "title":"Jurassic World",
	 "video":false,
	 "vote_average":7,
	 "vote_count":2257
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/sLbXneTErDvS3HIjqRWQJPiZ4Ci.jpg",
	 "genre_ids":[
	 10751,
	 16,
	 12,
	 35
	 ],
	 "id":211672,
	 "original_language":"en",
	 "original_title":"Minions",
	 "overview":"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.",
	 "release_date":"2015-06-25",
	 "poster_path":"\/q0R4crx2SehcEEQEkYObktdeFy.jpg",
	 "popularity":24.950162,
	 "title":"Minions",
	 "video":false,
	 "vote_average":6.9,
	 "vote_count":1327
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/bAfIhDOte1QKp6BpisfPPEcrROh.jpg",
	 "genre_ids":[
	 53,
	 28,
	 878
	 ],
	 "id":294254,
	 "original_language":"en",
	 "original_title":"Maze Runner: The Scorch Trials",
	 "overview":"Thomas and his fellow Gladers face their greatest challenge yet: searching for clues about the mysterious and powerful organization known as WCKD. Their journey takes them to the Scorch, a desolate landscape filled with unimaginable obstacles. Teaming up with resistance fighters, the Gladers take on WCKD’s vastly superior forces and uncover its shocking plans for them all.",
	 "release_date":"2015-09-18",
	 "poster_path":"\/vlTPQANjLYTebzFJM1G4KeON0cb.jpg",
	 "popularity":19.741519,
	 "title":"Maze Runner: The Scorch Trials",
	 "video":false,
	 "vote_average":6.8,
	 "vote_count":107
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/cUfGqafAVQkatQ7N4y08RNV3bgu.jpg",
	 "genre_ids":[
	 28,
	 18,
	 53
	 ],
	 "id":254128,
	 "original_language":"en",
	 "original_title":"San Andreas",
	 "overview":"In the aftermath of a massive earthquake in California, a rescue-chopper pilot makes a dangerous journey across the state in order to rescue his estranged daughter.",
	 "release_date":"2015-05-29",
	 "poster_path":"\/6iQ4CMtYorKFfAmXEpAQZMnA0Qe.jpg",
	 "popularity":18.536367,
	 "title":"San Andreas",
	 "video":false,
	 "vote_average":6.3,
	 "vote_count":790
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/bIlYH4l2AyYvEysmS2AOfjO7Dn8.jpg",
	 "genre_ids":[
	 878,
	 28,
	 53,
	 12
	 ],
	 "id":87101,
	 "original_language":"en",
	 "original_title":"Terminator Genisys",
	 "overview":"The year is 2029. John Connor, leader of the resistance continues the war against the machines. At the Los Angeles offensive, John's fears of the unknown future begin to emerge when TECOM spies reveal a new plot by SkyNet that will attack him from both fronts; past and future, and will ultimately change warfare forever.",
	 "release_date":"2015-07-01",
	 "poster_path":"\/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg",
	 "popularity":18.418502,
	 "title":"Terminator Genisys",
	 "video":false,
	 "vote_average":6.3,
	 "vote_count":957
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/2BXd0t9JdVqCp9sKf6kzMkr7QjB.jpg",
	 "genre_ids":[
	 12,
	 10751,
	 16,
	 28,
	 35
	 ],
	 "id":177572,
	 "original_language":"en",
	 "original_title":"Big Hero 6",
	 "overview":"The special bond that develops between plus-sized inflatable robot Baymax, and prodigy Hiro Hamada, who team up with a group of friends to form a band of high-tech heroes.",
	 "release_date":"2014-11-07",
	 "poster_path":"\/3zQvuSAUdC3mrx9vnSEpkFX0968.jpg",
	 "popularity":17.21983,
	 "title":"Big Hero 6",
	 "video":false,
	 "vote_average":7.9,
	 "vote_count":2307
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/L5QRL1O3fGs2hH1LbtYyVl8Tce.jpg",
	 "genre_ids":[
	 53,
	 28,
	 878,
	 10749
	 ],
	 "id":262500,
	 "original_language":"en",
	 "original_title":"Insurgent",
	 "overview":"Beatrice Prior must confront her inner demons and continue her fight against a powerful alliance which threatens to tear her society apart.",
	 "release_date":"2015-03-20",
	 "poster_path":"\/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg",
	 "popularity":16.317675,
	 "title":"Insurgent",
	 "video":false,
	 "vote_average":6.8,
	 "vote_count":1114
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/AoYGqtWxcNmQjQIpRCMtzpFfL1T.jpg",
	 "genre_ids":[
	 28,
	 35,
	 80
	 ],
	 "id":238713,
	 "original_language":"en",
	 "original_title":"Spy",
	 "overview":"A desk-bound CIA analyst volunteers to go undercover to infiltrate the world of a deadly arms dealer, and prevent diabolical global disaster.",
	 "release_date":"2015-06-05",
	 "poster_path":"\/49Akyhe0gnuokaDIKKDldFRBoru.jpg",
	 "popularity":14.597072,
	 "title":"Spy",
	 "video":false,
	 "vote_average":7.2,
	 "vote_count":673
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg",
	 "genre_ids":[
	 28,
	 12,
	 878
	 ],
	 "id":102899,
	 "original_language":"en",
	 "original_title":"Ant-Man",
	 "overview":"Armed with the astonishing ability to shrink in scale but increase in strength, con-man Scott Lang must embrace his inner-hero and help his mentor, Dr. Hank Pym, protect the secret behind his spectacular Ant-Man suit from a new generation of towering threats. Against seemingly insurmountable obstacles, Pym and Lang must plan and pull off a heist that will save the world.",
	 "release_date":"2015-07-17",
	 "poster_path":"\/7SGGUiTE6oc2fh9MjIk5M00dsQd.jpg",
	 "popularity":15.590741,
	 "title":"Ant-Man",
	 "video":false,
	 "vote_average":7,
	 "vote_count":1215
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/fUn5I5f4069vwGFEEvA3HXt9xPP.jpg",
	 "genre_ids":[
	 878,
	 12,
	 53
	 ],
	 "id":131631,
	 "original_language":"en",
	 "original_title":"The Hunger Games: Mockingjay - Part 1",
	 "overview":"Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol.",
	 "release_date":"2014-11-20",
	 "poster_path":"\/gj282Pniaa78ZJfbaixyLXnXEDI.jpg",
	 "popularity":15.069718,
	 "title":"The Hunger Games: Mockingjay - Part 1",
	 "video":false,
	 "vote_average":7,
	 "vote_count":1851
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/yTbPPmLAn7DiiM0sPYfZduoAjB.jpg",
	 "genre_ids":[
	 28,
	 9648,
	 878,
	 53
	 ],
	 "id":198663,
	 "original_language":"en",
	 "original_title":"The Maze Runner",
	 "overview":"Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow \"runners\" for a shot at escape.",
	 "release_date":"2014-09-19",
	 "poster_path":"\/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
	 "popularity":14.788201,
	 "title":"The Maze Runner",
	 "video":false,
	 "vote_average":7.1,
	 "vote_count":1954
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/57xvgxE5EUMnEVY9BV6lIx9jnip.jpg",
	 "genre_ids":[
	 28,
	 80,
	 53
	 ],
	 "id":249070,
	 "original_language":"en",
	 "original_title":"Hitman: Agent 47",
	 "overview":"An assassin teams up with a woman to help her find her father and uncover the mysteries of her ancestry.",
	 "release_date":"2015-08-21",
	 "poster_path":"\/4VmZeT8YkuMI6BrA623mHZDISlN.jpg",
	 "popularity":14.754624,
	 "title":"Hitman: Agent 47",
	 "video":false,
	 "vote_average":5.7,
	 "vote_count":172
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/i5oEwnSJpbi8L1HOCP3aibkExHU.jpg",
	 "genre_ids":[
	 27,
	 53
	 ],
	 "id":243688,
	 "original_language":"en",
	 "original_title":"Poltergeist",
	 "overview":"Legendary filmmaker Sam Raimi and director Gil Kenan reimagine and contemporize the classic tale about a family whose suburban home is invaded by angry spirits. When the terrifying apparitions escalate their attacks and take the youngest daughter, the family must come together to rescue her.",
	 "release_date":"2015-05-22",
	 "poster_path":"\/fWOPN0XBvHXFYr3RsPr74qBge2I.jpg",
	 "popularity":14.719376,
	 "title":"Poltergeist",
	 "video":false,
	 "vote_average":5.1,
	 "vote_count":257
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/ypyeMfKydpyuuTMdp36rMlkGDUL.jpg",
	 "genre_ids":[
	 28,
	 12,
	 80,
	 53,
	 10749
	 ],
	 "id":168259,
	 "original_language":"en",
	 "original_title":"Furious 7",
	 "overview":"Deckard Shaw seeks revenge against Dominic Toretto and his family for his comatose brother.",
	 "release_date":"2015-04-03",
	 "poster_path":"\/dCgm7efXDmiABSdWDHBDBx2jwmn.jpg",
	 "popularity":13.624272,
	 "title":"Furious 7",
	 "video":false,
	 "vote_average":7.6,
	 "vote_count":1633
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg",
	 "genre_ids":[
	 18,
	 878
	 ],
	 "id":157336,
	 "original_language":"en",
	 "original_title":"Interstellar",
	 "overview":"Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
	 "release_date":"2014-11-05",
	 "poster_path":"\/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
	 "popularity":13.734016,
	 "title":"Interstellar",
	 "video":false,
	 "vote_average":8.3,
	 "vote_count":3289
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg",
	 "genre_ids":[
	 878,
	 14,
	 12
	 ],
	 "id":118340,
	 "original_language":"en",
	 "original_title":"Guardians of the Galaxy",
	 "overview":"Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.",
	 "release_date":"2014-08-01",
	 "poster_path":"\/9gm3lL8JMTTmc3W4BmNMCuRLdL8.jpg",
	 "popularity":13.403702,
	 "title":"Guardians of the Galaxy",
	 "video":false,
	 "vote_average":8.1,
	 "vote_count":3439
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/eh2Me4oTfjASj2musonkjsod5Mq.jpg",
	 "genre_ids":[
	 35
	 ],
	 "id":188222,
	 "original_language":"en",
	 "original_title":"Entourage",
	 "overview":"Movie star Vincent Chase, together with his boys, Eric, Turtle and Johnny, are back…and back in business with super agent-turned-studio head Ari Gold. Some of their ambitions have changed, but the bond between them remains strong as they navigate the capricious and often cutthroat world of Hollywood.",
	 "release_date":"2015-06-03",
	 "poster_path":"\/lR4dwcfgCBMklKjiQXuFzRM3gfl.jpg",
	 "popularity":13.305182,
	 "title":"Entourage",
	 "video":false,
	 "vote_average":6.2,
	 "vote_count":144
	 },
	 {
	 "adult":false,
	 "backdrop_path":"\/aUYcExsGuRaw7PLGmAmXubt1dfG.jpg",
	 "genre_ids":[
	 10749,
	 14,
	 10751,
	 18
	 ],
	 "id":150689,
	 "original_language":"en",
	 "original_title":"Cinderella",
	 "overview":"When her father unexpectedly passes away, young Ella finds herself at the mercy of her cruel stepmother and her daughters. Never one to give up hope, Ella's fortunes begin to change after meeting a dashing stranger in the woods.",
	 "release_date":"2015-03-13",
	 "poster_path":"\/2i0JH5WqYFqki7WDhUW56Sg0obh.jpg",
	 "popularity":13.256035,
	 "title":"Cinderella",
	 "video":false,
	 "vote_average":7,
	 "vote_count":671
	 }
	 ],
	 "total_pages":12190,
	 "total_results":243796
	 }
	 */

}
