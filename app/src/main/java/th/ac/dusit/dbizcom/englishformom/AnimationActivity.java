package th.ac.dusit.dbizcom.englishformom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import th.ac.dusit.dbizcom.englishformom.etc.Utils;
import th.ac.dusit.dbizcom.englishformom.model.Animation;
import th.ac.dusit.dbizcom.englishformom.model.Sentence;
import th.ac.dusit.dbizcom.englishformom.net.ApiClient;
import th.ac.dusit.dbizcom.englishformom.net.GetAnimationResponse;
import th.ac.dusit.dbizcom.englishformom.net.MyRetrofitCallback;
import th.ac.dusit.dbizcom.englishformom.net.WebServices;

import static th.ac.dusit.dbizcom.englishformom.VideoActivity.KEY_VIDEO_URL;

public class AnimationActivity extends AppCompatActivity {

    private List<Animation> mAnimationList = null;

    private ProgressBar mProgressView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        if (mAnimationList == null) {
            doGetAnimation();
        }
    }

    private void doGetAnimation() {
        mProgressView = findViewById(R.id.progress_view);
        mProgressView.setVisibility(View.VISIBLE);

        Retrofit retrofit = ApiClient.getClient();
        WebServices services = retrofit.create(WebServices.class);

        Call<GetAnimationResponse> call = services.getAnimation();
        call.enqueue(new MyRetrofitCallback<>(
                this,
                null,
                mProgressView,
                new MyRetrofitCallback.MyRetrofitCallbackListener<GetAnimationResponse>() {
                    @Override
                    public void onSuccess(GetAnimationResponse responseBody) {
                        mAnimationList = responseBody.animationList;
                        setupView();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Utils.showOkDialog(
                                AnimationActivity.this,
                                "Error",
                                errorMessage,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }
                        );
                    }
                }
        ));
    }

    private void setupView() {
        AnimationListAdapter adapter = new AnimationListAdapter(
                this,
                mAnimationList,
                new AnimationActivityListener() {
                    @Override
                    public void onClickAnimation(Animation animation) {
                        Intent intent = new Intent(AnimationActivity.this, VideoActivity.class);
                        intent.putExtra(KEY_VIDEO_URL, animation.videoUrl);
                        startActivity(intent);
                    }
                }
        );

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpacingDecoration(this));
        mRecyclerView.setAdapter(adapter);
    }

    /*@Override
    public void onClick(View v) {
        String videoUrl = null;

        switch (v.getId()) {
            case R.id.morning_image_view:
                videoUrl = getString(R.string.video_url_1);
                break;
            case R.id.school_image_view:
                //videoUrl = "";
                break;
            case R.id.playground_image_view:
                videoUrl = getString(R.string.video_url_2);
                break;
            case R.id.eat_image_view:
                //videoUrl = "";
                break;
            case R.id.holiday_text_view:
                videoUrl = "";
                //break;
        }

        if (videoUrl == null) {
            Utils.showLongToast(this, "ยังไม่เสร็จครับ");
            return;
        }

        Intent intent = new Intent(AnimationActivity.this, VideoActivity.class);
        intent.putExtra(KEY_VIDEO_URL, videoUrl);
        startActivity(intent);
    }*/

    public interface AnimationActivityListener {
        void onClickAnimation(Animation animation);
    }

    private static class AnimationListAdapter extends RecyclerView.Adapter<AnimationListAdapter.ViewHolder> {

        private final Context mContext;
        private final List<Animation> mAnimationList;
        private final AnimationActivityListener mListener;

        AnimationListAdapter(Context context, List<Animation> animationList, AnimationActivityListener listener) {
            mContext = context;
            mAnimationList = animationList;
            mListener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_animation, parent, false
            );
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder h, int position) {
            final Animation animation = mAnimationList.get(position);

            h.mAnimation = animation;

            int categoryImageResId = 0;
            int thumbImageResId = 0;
            String duration = null;
            switch (animation.category) {
                case Sentence.CATEGORY_MORNING:
                    categoryImageResId = R.drawable.title_category_morning;
                    thumbImageResId = R.drawable.thumb_morning;
                    duration = "1:35";
                    break;
                case Sentence.CATEGORY_SCHOOL:
                    categoryImageResId = R.drawable.title_category_school;
                    thumbImageResId = R.drawable.thumb_school;
                    duration = "2:25";
                    break;
                case Sentence.CATEGORY_PLAYGROUND:
                    categoryImageResId = R.drawable.title_category_playground;
                    thumbImageResId = R.drawable.thumb_playground;
                    duration = "1:44";
                    break;
                case Sentence.CATEGORY_EAT:
                    categoryImageResId = R.drawable.title_category_eat;
                    thumbImageResId = R.drawable.thumb_eat;
                    duration = "1:57";
                    break;
                case Sentence.CATEGORY_WEEKEND:
                    categoryImageResId = R.drawable.title_category_weekend;
                    thumbImageResId = R.drawable.thumb_weekend;
                    duration = "1:41";
                    break;
            }

            h.mCategoryImageView.setImageResource(categoryImageResId);
            h.mVideoImageView.setImageResource(thumbImageResId);
            h.mDurationTextView.setText(duration);

            /*CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mContext);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();

            Glide.with(mContext)
                    .load(ApiClient.IMAGE_BASE_URL.concat(animation.coverImage))
                    .placeholder(circularProgressDrawable)
                    .into(holder.mProjectImageView);*/
        }

        @Override
        public int getItemCount() {
            return mAnimationList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView mVideoImageView;
            private final ImageView mCategoryImageView;
            private final TextView mDurationTextView;

            private Animation mAnimation;

            ViewHolder(View itemView) {
                super(itemView);

                mVideoImageView = itemView.findViewById(R.id.video_image_view);
                mCategoryImageView = itemView.findViewById(R.id.category_image_view);
                mDurationTextView = itemView.findViewById(R.id.word_text_view);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onClickAnimation(mAnimation);
                    }
                });
            }
        }
    }

    public class SpacingDecoration extends RecyclerView.ItemDecoration {

        private final static int MARGIN_TOP_IN_DP = 0;
        private final static int MARGIN_BOTTOM_IN_DP = 16;
        private final int mMarginTop, mMarginBottom;

        SpacingDecoration(@NonNull Context context) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            mMarginTop = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    MARGIN_TOP_IN_DP,
                    metrics
            );
            mMarginBottom = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    MARGIN_BOTTOM_IN_DP,
                    metrics
            );
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            final int itemPosition = parent.getChildAdapterPosition(view);
            if (itemPosition == RecyclerView.NO_POSITION) {
                return;
            }
            if (itemPosition == 0) {
                outRect.top = mMarginTop;
            }
            final RecyclerView.Adapter adapter = parent.getAdapter();
            if ((adapter != null) && (itemPosition == adapter.getItemCount() - 1)) {
                outRect.bottom = mMarginBottom;
            }
        }
    }
}
